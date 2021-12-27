package com.zukxu.rabbitmq.model.direct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
	//	声明注册direct模式的交换机
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("direct_order_exchange", true, false);
	}

	//	声明所要用到的队列：sms msg emile
	@Bean
	public Queue smsQueue() {
		return new Queue("direct_sms_queue");
	}

	@Bean
	public Queue msgQueue() {
		return new Queue("direct_msg_queue");
	}

	@Bean
	public Queue emailQueue() {
		return new Queue("direct_email_queue");
	}

	//	完成交换机和队列,路由key的绑定
	@Bean
	public Binding smsBinding() {
		return BindingBuilder.bind(smsQueue()).to(directExchange()).with("sms");
	}

	@Bean
	public Binding msgBinding() {
		return BindingBuilder.bind(msgQueue()).to(directExchange()).with("msg");
	}

	@Bean
	public Binding emailBinding() {
		return BindingBuilder.bind(emailQueue()).to(directExchange()).with("email");
	}
}
