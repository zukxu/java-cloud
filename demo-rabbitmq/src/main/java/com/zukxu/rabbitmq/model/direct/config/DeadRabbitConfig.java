package com.zukxu.rabbitmq.model.direct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zukxu
 * CreateTime: 2021/5/21 0021 12:26
 */

@Configuration
public class DeadRabbitConfig {
    //	声明注册direct模式的交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_direct_exchange", true, false);
    }

    //	设置死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue("dead_direct_queue", true);
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }
}
