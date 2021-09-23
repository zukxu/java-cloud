# 伪代码实现

## MQ架构实现

响应时间为业务执行时间+MQ消息投递的执行时间，但是MQ投递速度很快，相当于忽略不计

```java

package com.zukxu.test.mq;

/**
 * 同步串行执行代码流程
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 17:19
 */
public class makeOrderMQ {
    public void makeOrderMQ() {
        //	1.保存订单
        orderService.save();
        //	2.MQ执行相关服务
        rabbitmqTemplate.convertSend();
    }
}
```