package com.zukxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zukxu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AlipayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlipayApplication.class, args);
    }

}
