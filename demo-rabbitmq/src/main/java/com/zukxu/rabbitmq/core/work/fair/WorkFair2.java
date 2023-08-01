package com.zukxu.rabbitmq.core.work.fair;

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
public class WorkFair2 {
    private static Logger log = LoggerFactory.getLogger(WorkFair2.class);

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
            connection = connectionFactory.newConnection("消费者-Work2");

            //	3. 通过连接获取通道Chanel
            channel = connection.createChannel();

            //	4. 声明队列，如果不存在会自动创建
            //同一时刻服务器只会推送一条消息给消费者
            String queueName = "q2";

            //5. 定义接收消息的回调
            Channel callbackChannel = channel;
            //制定qos指标默认为null，即为轮询分发： qos=1 一次性取出多少条数据进行消费
            //根据服务器性能进行设置
            callbackChannel.basicQos(1);
            /**
             * @param 队列名称
             * @param 应答机制(自动应答可能会造成死循环)
             * @param 响应函数
             *
             */
            callbackChannel.basicConsume(queueName, false, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    log.info("work2 消息接收成功：{}", new String(delivery.getBody(), StandardCharsets.UTF_8));
                    //	模拟服务器性能
                    try {
                        Thread.sleep(200);
                        //手动应答
                        callbackChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    log.error("接受消息失败:{}", s);
                }
            });

            System.out.println("work2开始接收消息");
            System.in.read();
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
