package com.zukxu.rabbitmq.model.fanout.service;

import lombok.extern.slf4j.Slf4j;
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
    public boolean fanoutOrder(String userId, String productId, Integer num) {
        //保存订单业务执行
        String orderId = UUID.randomUUID().toString();
        log.info("订单业务执行完毕");
        log.info("开始异步执行消息分发");
        //通过MQ进行消息分发
        //定义交换机
        String exchangeName = "fanout_order_exchange";
        //定义路由key
        String routingKey = "";

        //消息分发
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
        return false;
    }
}
