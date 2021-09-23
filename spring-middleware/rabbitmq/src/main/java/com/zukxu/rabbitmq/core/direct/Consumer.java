package com.zukxu.rabbitmq.core.direct;

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
	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
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

			//获取队列的名称
			final String queueName = Thread.currentThread().getName();

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

				log.info("参数传递队列名称：{}", queueName);

				//5. 声明队列存储消息，如果不存在则被创建，rabbitmq不允许存在两个相同的队列名称
				log.info("5. 声明队列存储消息");

				//6. 定义接收消息的回调
				log.info("6. 定义接收消息的回调");
				Channel callbackChannel = channel;
				/**
				 * @param queue 队列名称
				 * @param durable 是否持久化
				 * @param exclusive 是否排他，是否是私有的，如果为true，则会对当前队列加锁，其他的通道不能访问，并且连接自动关闭
				 * @param autoDelete  是否自动删除 当最后一个消费者断开连接之后是否自动删除消息
				 * @param arguments 可以设置队列附加参数，设置队列的有效期，消息的最大长度，队列的生命周期
				 */
				callbackChannel.basicConsume(queueName, true, new DeliverCallback() {
					@Override
					public void handle(String s, Delivery delivery) throws IOException {
						log.info(String.valueOf(delivery.getEnvelope().getDeliveryTag()));
						log.info("{}：收到的消息是：{}",queueName, new String(delivery.getBody(), StandardCharsets.UTF_8));
					}
				}, new CancelCallback() {
					@Override
					public void handle(String s) throws IOException {
						log.error("接受消息失败:{}", s);
					}
				});

				System.out.println("开始接收消息");
				//阻断执行
				// System.in.read();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				log.info("关闭连接");
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
	};

	public static void main(String[] args) {
		//采用多线程的方式进行创建，使用线程名作为队列名
		new Thread(runnable,"q1").start();
		new Thread(runnable,"q2").start();
		new Thread(runnable,"q3").start();
	}
}
