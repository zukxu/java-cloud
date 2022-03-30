package com.zukxu.test.proxyBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 11:54
 */
//@Configuration(proxyBeanMethods = true)
@Configuration(proxyBeanMethods = false)
public class TestConfig {
    @Bean
    public OrderEntity getOrderEntity() {
        return new OrderEntity();
    }

    @Bean
    public MyBean1 myBean1() {
        return new MyBean1();
    }

    @Bean
    public MyBean2 MyBean2() {
        return new MyBean2(myBean1());
    }
}