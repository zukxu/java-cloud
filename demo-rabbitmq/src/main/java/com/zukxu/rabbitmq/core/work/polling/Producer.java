package com.zukxu.rabbitmq.core.work.polling;

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
        //	所有中间件都是基于tcp/ip协议，rabbitmq基于AMQP协议
        //	需要ip:port

        //	步骤
        //	1. 创建连接工厂
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
            connection = connectionFactory.newConnection("生产者-1");

            //	3. 通过连接获取通道Chanel
            channel = connection.createChannel();

            //	4. 通过通道创建交换机，声明队列，绑定关系，路由key，发送消息，接收消息
            //声明队列
            String queueName = "q1";
            /**
             * @param 队列名称
             * @param 是否持久化
             * @param 是否独占队列（排他性，一般不设置为排他）
             * @param 是否自动删除（最后一个消费者消费结束是否自动删除，一般不会自动删除）
             * @param 附加参数map
             *
             */
            channel.queueDeclare(queueName, true, false, false, null);

            //	5. 准备消息

            String msg = null;
            for (int i = 1; i <= 20; i++) {
                msg = "WORK:POLLING:" + i;

                //	6. 发送消息到queue
                /**
                 * @param 交换机(如果没有指定交换机，则第二个参数为队列名)
                 * @param 队列名称
                 * @param 消息持久化
                 * @param 发送内容
                 */
                channel.basicPublish("", queueName, null, msg.getBytes());
            }
            log.info("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
