package com.zukxu.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-29 11:24
 */
@SpringBootApplication(scanBasePackages = {"com.zukxu.common.config"})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }
}
