package com.zukxu.rabbitmq.model.direct.config;

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

    @Bean
    public DirectExchange ttlMessageDirectExchange() {
        return new DirectExchange("ttl_message_direct_exchange", true, false);
    }

    //	设置队列过期时间
    @Bean
    public Queue ttlQueue() {
        //设置队列参数
        Map<String, Object> args = new HashMap<>();
        //x-message-ttl: 表示过期时间
        args.put("x-message-ttl", 5000);
        //绑定死信交换机
        args.put("x-dead-letter-exchange", "dead_direct_exchange");
        //绑定死信key direct模式下需要配置
        args.put("x-dead-letter-routing-key", "dead");
        return new Queue("ttl_direct_queue", true, false, false, args);
    }

    //	设置消息过期时间
    @Bean
    public Queue ttlMessageQueue() {
        return new Queue("ttl_message_direct_queue", true);
    }

    //	完成交换机和队列,路由key的绑定
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl");
    }

    @Bean
    public Binding ttlMessageBinding() {
        return BindingBuilder.bind(ttlMessageQueue()).to(ttlMessageDirectExchange()).with("ttL_message");
    }
}
