package com.zukxu.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zukxu
 * CreateTime: 2021/5/21 0021 12:26
 */

@Configuration
public class TTLRabbitConfig {
    //	声明注册direct模式的交换机
    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl_direct_exchange", true, false);
    }

    //	设置队列过期时间
    @Bean
    public Queue ttlQueue() {
        //设置队列参数
        Map<String, Object> args = new HashMap<>();
        //x-message-ttl: 表示过期时间
        args.put("x-message-ttl", 5000);
        return new Queue("ttl_direct_queue", true, false, false, args);
    }

    //	完成交换机和队列,路由key的绑定
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl");
    }
}
