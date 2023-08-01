## 简介

### 描述

- xxl-job是一个开源的分布式定时任务;
    - 即：把分散的、可靠性差的计划任务纳入统一的平台，并实现集群管理调度和分布式部署的一种定时任务的管理方式。叫做分布式定时任务
- xxl-job由许雪里个人开源的分布式定时任务系统;
- 该定时任务主要是用java语言来写的系统。
-

### 项目地址

> Github: https://github.com/xuxueli/xxl-job  
> Gitee: https://gitee.com/xuxueli0323/xxl-job  
> 官方文档：https://www.xuxueli.com/xxl-job/#%E3%80%8A%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6%E5%B9%B3%E5%8F%B0XXL-JOB%E3%80%8B

## 环境准备

- 依赖

```xml
<dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-job-core</artifactId>
    <version>2.3.1</version>
</dependency>
```

## 设计思想

### 1. 使用动态代理模式，隐藏通信细节

xxl-job 定义了两个接口 【ExecutorBiz，AdminBiz】

- ExecutorBiz 接口中封装了向心跳，暂停，触发执行等操作
- AdminBiz 封装了回调，注册，取消注册操作，接口的实现类中，并没有通信相关的处理。

**XxlRpcReferenceBean** 类的 getObject() 方法会生成一个代理类，这个代理类会进行远程通信。

### 2. 全异步处理

执行器收到消息进行反序列化，并没有同步执行任务代码，而是将任务信息存储在 **LinkedBlockingQueue** 中，  
异步线程从这个队列中获取任务信息，然后执行。  
而任务的处理结果，也不是说处理完之后，同步返回的，也是放到回调线程的阻塞队列中，异步的将处理结果返回回去。
这样处理的好处就是减少了 netty 工作线程的处理时间，提升了吞吐量。

- 对异步处理的包装  
  对异步处理进行了包装，代码看起来是同步调用的。  
  我们看下调度器，XxlJobTrigger 类触发任务执行的代码：

```java
public static ReturnT<String> runExecutor(TriggerParam triggerParam, String address){
    ReturnT<String> runResult = null;
    try {
        ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
        //这里面做了很多异步处理，最终同步得到处理结果
        runResult = executorBiz.run(triggerParam);
    } catch (Exception e) {
        logger.error(">>>>>>>>>>> xxl-job trigger error, please check if the executor[{}] is running.", address, e);
        runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ThrowableUtil.toString(e));
    }

    StringBuffer runResultSB = new StringBuffer(I18nUtil.getString("jobconf_trigger_run") + "：");
    runResultSB.append("<br>address：").append(address);
    runResultSB.append("<br>code：").append(runResult.getCode());
    runResultSB.append("<br>msg：").append(runResult.getMsg());

    runResult.setMsg(runResultSB.toString());
    return runResult;
}
```

ExecutorBiz.run 方法我们说过了，是走的动态代理，和执行器进行通信，执行器执行结果也是异步处理完，才返回的，而这里看到的 run 方法是同步等待处理结果返回

- xxl-job是如何同步获取处理结果的：

1. 调度器向执行器发出消息后，该线程阻塞。
2. 等到执行器处理完毕后，将处理结果返回，
3. 唤醒被阻塞的线程，调用处拿到返回值。

其中部份逻辑代码如下所示：

```java
if (CallType.SYNC == callType) {
   // future-response set
   XxlRpcFutureResponse futureResponse = new XxlRpcFutureResponse(invokerFactory, xxlRpcRequest, null);
   try {
      // do invoke
      client.asyncSend(finalAddress, xxlRpcRequest);

      // future get
      XxlRpcResponse xxlRpcResponse = futureResponse.get(timeout, TimeUnit.MILLISECONDS);
      if (xxlRpcResponse.getErrorMsg() != null) {
         throw new XxlRpcException(xxlRpcResponse.getErrorMsg());
      }
      return xxlRpcResponse.getResult();
   } catch (Exception e) {
      logger.info(">>>>>>>>>>> xxl-rpc, invoke error, address:{}, XxlRpcRequest{}", finalAddress, xxlRpcRequest);

      throw (e instanceof XxlRpcException)?e:new XxlRpcException(e);
   } finally{
      // future-response remove
      futureResponse.removeInvokerFuture();
   }
} 
```

- XxlRpcFutureResponse 类中实现了线程的等待，和线程唤醒的处理

```java
//返回结果，唤醒线程
    public void setResponse(XxlRpcResponse response) {
       this.response = response;
       synchronized (lock) {
          done = true;
          lock.notifyAll();
       }
    }

    @Override
    public XxlRpcResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!done) {
            synchronized (lock) {
                try {
                    if (timeout < 0) {
            //线程阻塞
                        lock.wait();
                    } else {
                        long timeoutMillis = (TimeUnit.MILLISECONDS==unit)?timeout:TimeUnit.MILLISECONDS.convert(timeout , unit);
                        lock.wait(timeoutMillis);
                    }
                } catch (InterruptedException e) {
                    throw e;
                }
            }
        }

        if (!done) {
            throw new XxlRpcException("xxl-rpc, request timeout at:"+ System.currentTimeMillis() +", request:" + request.toString());
        }
        return response;
    }
```

- 如何进行调度器唤醒线程

1. 每一次远程调用，都会生成 uuid 的请求 id，这个 id 是在整个调用过程中一直传递的
2. 这里拿着请求 id 就能找到对应的 XxlRpcFutureResponse，然后调用 setResponse 方法，设置返回值，唤醒线程

逻辑如下：

```java
public void notifyInvokerFuture(String requestId, final XxlRpcResponse xxlRpcResponse){

    // 通过requestId找到XxlRpcFutureResponse，
    final XxlRpcFutureResponse futureResponse = futureResponsePool.get(requestId);
    if (futureResponse == null) {
        return;
    }
    if (futureResponse.getInvokeCallback()!=null) {

        // callback type
        try {
            executeResponseCallback(new Runnable() {
                @Override
                public void run() {
                    if (xxlRpcResponse.getErrorMsg() != null) {
                        futureResponse.getInvokeCallback().onFailure(new XxlRpcException(xxlRpcResponse.getErrorMsg()));
                    } else {
                        futureResponse.getInvokeCallback().onSuccess(xxlRpcResponse.getResult());
                    }
                }
            });
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    } else {
        // 里面调用lock的notify方法
        futureResponse.setResponse(xxlRpcResponse);
    }

    // do remove
    futureResponsePool.remove(requestId);

}
```

## 使用

具体项目使用可参考官方文档：https://www.xuxueli.com/xxl-job/#2.1%20%E5%88%9D%E5%A7%8B%E5%8C%96%E2%80%9C%E8%B0%83%E5%BA%A6%E6%95%B0%E6%8D%AE%E5%BA%93%E2%80%9D  
简单使用xxl-job：https://www.yuque.com/docs/share/95a06803-d739-46e6-91e7-7e7646805467?# 《简单实用xxl-job》