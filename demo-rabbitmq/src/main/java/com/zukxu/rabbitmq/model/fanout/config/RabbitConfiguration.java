package com.zukxu.rabbitmq.model.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit config
 *
 * @author zukxu
 * CreateTime: 2021/5/20 0020 14:22
 */
@Configuration
public class RabbitConfiguration {
    //	声明注册fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_order_exchange", true, false);
    }

    //	声明所要用到的队列：sms msg emile
    @Bean
    public Queue smsQueue() {
        return new Queue("fanout_sms_queue");
    }

    @Bean
    public Queue msgQueue() {
        return new Queue("fanout_msg_queue");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("fanout_email_queue");
    }

    //	完成交换机和队列的绑定
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(msgQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
}
