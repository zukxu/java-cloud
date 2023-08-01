# 伪代码实现

## 传统串行执行代码流程

串行执行代码，程序响应时间为所有代码执行完毕时间

```java

/**
 *同步串行执行代码流程
 *
 * @author zukxu
 * CreateTime: 2021/5/19 0019 17:19
 */
public class makeOrder {
    //	1.保存订单
	orderService.save();
    //	2.发送短信服务
	messageService.sendMessage();
    //	3.发送emile服务
	emileService.sendEmile();
    //	4.发送APP通知服务
	appService.sendApp();
}
```