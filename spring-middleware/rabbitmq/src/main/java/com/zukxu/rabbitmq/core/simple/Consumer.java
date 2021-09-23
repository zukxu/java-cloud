package com.zukxu.rabbitmq.core.simple;

import cn.hutool.core.util.ObjectUtil;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author zukxu
 * CreateTime: 2021/5/18 0018 15:32
 */
public class Consumer {
	private static Logger log = LoggerFactory.getLogger(Consumer.class);

	public static void main(String[] args) {
		//	所有中间件都是基于tcp/ip协议，rabbitmq基于AMQP协议
		//	需要ip:port

		//	步骤
		//	1. 创建连接工厂
		log.info("1. 创建连接工厂");
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//配置相关配置项
		connectionFactory.setHost("112.74.175.76");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setVirtualHost("/");

		//声明连接和通道
		Connection connection = null;
		Channel channel = null;
		try {
			// 	2. 创建连接 Connection
			log.info("2. 创建连接 Connection");
			connection = connectionFactory.newConnection("消费者-1");

			//	3. 通过连接获取通道Chanel
			log.info("3. 通过连接获取通道Chanel");
			channel = connection.createChannel();

			//	4. 通过通道创建交换机，声明队列，绑定关系，路由key，发送消息，接收消息
			//声明队列
			log.info("4. 通过通道接收消息");

			String queueName = "队列-1";
			log.info("参数传递队列名称：{}", queueName);

			channel.basicConsume(queueName, true, new DeliverCallback() {
				@Override
				public void handle(String s, Delivery delivery) throws IOException {
					log.info("消息接收成功：{}", new String(delivery.getBody(), StandardCharsets.UTF_8));
				}
			}, new CancelCallback() {
				@Override
				public void handle(String s) throws IOException {
					log.error("接受消息失败:{}", s);
				}
			});

			System.out.println("开始接收消息");
			//阻断执行
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			log.info("7. 关闭连接");
			//	7. 关闭通道

			if (ObjectUtil.isNotEmpty(channel) && channel.isOpen()) {

				try {
					channel.close();
				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}

			//	8. 关闭连接
			if (ObjectUtil.isNotEmpty(connection) && connection.isOpen()) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
