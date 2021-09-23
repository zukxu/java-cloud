package com.zukxu.rabbitmq.consumer.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zukxu
 * CreateTime: 2021/5/20 0020 15:14
 */
@Component
@RabbitListener(queues = {"direct_msg_queue"})
public class DirectMsgConsumer {
	@RabbitHandler
	public void receiveMsg(String message) {
		System.out.println("direct ：direct_msg_queue---->" + message);
	}

}
