package com.zukxu.rabbitmq.consumer.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author zukxu
 * CreateTime: 2021/5/20 0020 15:14
 */
@Component
@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "topic_email_queue",durable = "true",autoDelete = "false"),
		exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
		key = "#.email.#"
))
public class TopicEmailConsumer {
	@RabbitHandler
	public void receiveMsg(String message) {
		System.out.println("topic ：topic_email_queue---->"+message);
	}

}
