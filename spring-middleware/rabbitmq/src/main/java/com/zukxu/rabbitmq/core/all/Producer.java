package com.zukxu.rabbitmq.core.all;

import cn.hutool.core.util.ObjectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author zukxu
 * CreateTime: 2021/5/18 0018 15:32
 */
public class Producer {
	private static Logger log = LoggerFactory.getLogger(Producer.class);

	public static void main(String[] args) {

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
			connection = connectionFactory.newConnection("生产者");

			//	3. 通过连接获取通道Chanel
			log.info("3. 通过连接获取通道Chanel");
			channel = connection.createChannel();

			//	5. 准备消息
			log.info("5. 准备消息");
			String msg = "投递消息到 direct-message-exchange "+System.currentTimeMillis();

			//	6. 准备新的交换机
			log.info("6. 准备新的交换机");
			String exchangeName = "direct-message-exchange";

			//	7. 定义路由key
			// log.info("7. 定义路由key");
			// String routingKey = "message";

			//	8. 定义交换机类型
			log.info("8. 定义交换机类型");
			String exchangeType = "direct";

			//如果交换机不存在需要先声明交换机 1. 代码声明 2. web界面
			/**
			 * @param 交换机名称
			 * @param 交换机类型
			 * @param 是否持久化
			 */
			channel.exchangeDeclare(exchangeName, exchangeType, true);

			//声明队列
			/**
			 * @param 队列名称
			 * @param 是否持久化
			 * @param 是否独占队列（排他性，一般不设置为排他）
			 * @param 是否自动删除（最后一个消费者消费结束是否自动删除，一般不会自动删除）
			 * @param 附加参数map
			 *
			 */
			channel.queueDeclare("q5",true,false,false,null);
			channel.queueDeclare("q6",true,false,false,null);
			channel.queueDeclare("q7",true,false,false,null);

			//交换机绑定队列
			/**
			 * @param 队列名称
			 * @param 交换机名称
			 * @param 路由key
			 */
			channel.queueBind("q5", exchangeName, "order");
			channel.queueBind("q6", exchangeName, "order");
			channel.queueBind("q7", exchangeName, "course");

			//发送消息
			/**
			 * @param 交换机
			 * @param 队列名称/路由key
			 * @param 属性配置
			 * @param 发送消息内容
			 */
			channel.basicPublish(exchangeName,"course",null,msg.getBytes());
			log.info("消息发送成功：{}",msg);
		} catch (Exception e) {
			log.info("消息发送异常");
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
}
