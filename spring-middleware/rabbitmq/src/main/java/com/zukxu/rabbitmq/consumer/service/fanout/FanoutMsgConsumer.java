package com.zukxu.rabbitmq.consumer.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zukxu
 * CreateTime: 2021/5/20 0020 15:14
 */
@Component
@RabbitListener(queues = {"fanout_msg_queue"})
public class FanoutMsgConsumer {
	@RabbitHandler
	public void receiveMsg(String message) {
		System.out.println("fanout ：fanout_msg_queue---->" + message);
	}

}
