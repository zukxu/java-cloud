# 伪代码实现

## 使用线程池并行执行代码流程
并行执行代码：  
响应时间为业务执行时间+消息发送的最长的执行时间

```java
/**
 * 同步串行执行代码流程
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 17:19
 */
public class makeOrderThread {
	public void makeOrderThread() {
		//	1.保存订单
		orderService.save();
		//	2.多线程执行相关服务
		relationService();
	}

	public void relationService() {
		//	多线程执行
		threadpool.submit(new Callable<Object>{

		//	2.发送短信服务
			messageService.sendMessage();
		});
		//	3.发送emile服务
		threadpool.submit(new Callable<Object> {
			emileService.sendEmile();
		});
		//	4.发送APP通知服务
		threadpool.submit(new Callable<Object> {
			appService.sendApp();
		});
	}
}
```