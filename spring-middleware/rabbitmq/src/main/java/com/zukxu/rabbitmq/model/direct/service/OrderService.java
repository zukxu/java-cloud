package com.zukxu.rabbitmq.model.direct.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * order service
 *
 * @author zukxu
 * CreateTime: 2021/5/20 0020 14:15
 */
@Slf4j
@Service
public class OrderService {
	//	获取rabbitmq 对象
	@Autowired
	RabbitTemplate rabbitTemplate;

	/**
	 * 模拟下订单
	 *
	 * @return
	 */
	public boolean directOrder(String userId, String productId, Integer num) {
		//保存订单业务执行
		String orderId = UUID.randomUUID().toString();
		log.info("订单业务执行完毕");
		log.info("开始异步执行消息分发");
		//通过MQ进行消息分发
		//定义交换机
		String exchangeName = "direct_order_exchange";

		//消息分发
		rabbitTemplate.convertAndSend(exchangeName, "email", orderId);
		rabbitTemplate.convertAndSend(exchangeName, "sms", orderId);
		return false;
	}

	/**
	 * 配置ttl
	 *
	 * @param userId
	 * @param productId
	 * @param num
	 * @return
	 */
	public boolean ttlDirectOrder(String userId, String productId, Integer num) {
		//保存订单业务执行
		String orderId = UUID.randomUUID().toString();
		log.info("订单业务执行完毕");
		log.info("开始异步执行消息分发");
		//通过MQ进行消息分发
		//定义交换机
		String exchangeName = "ttl_direct_exchange";

		//消息分发
		rabbitTemplate.convertAndSend(exchangeName, "ttl", orderId);
		return false;
	}

	/**
	 * 给消息设置TTL
	 *
	 * @param userId
	 * @param productId
	 * @param num
	 * @return
	 */
	public boolean ttlDirectOrderMessage(String userId, String productId, Integer num) {
		//保存订单业务执行
		String orderId = UUID.randomUUID().toString();
		log.info("订单业务执行完毕");
		log.info("开始异步执行消息分发");
		//通过MQ进行消息分发
		//定义交换机
		String exchangeName = "ttl_message_direct_exchange";
		String routingKey = "ttL_message";

		//给消息设置TTL

		MessagePostProcessor processor = new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				//设置过期时间
				message.getMessageProperties().setExpiration("5000");
				message.getMessageProperties().setContentEncoding("UTF-8");
				return message;
			}
		};
		//消息分发
		rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
		return false;
	}
}
