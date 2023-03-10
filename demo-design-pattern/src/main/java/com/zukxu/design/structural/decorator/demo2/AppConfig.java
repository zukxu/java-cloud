package com.zukxu.design.structural.decorator.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/10 16:24:35
 */
@Configuration
public class AppConfig {

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    public OrderService loggingOrderService() {
        return new LoggingOrderServiceDecorator(orderService());
    }

}
