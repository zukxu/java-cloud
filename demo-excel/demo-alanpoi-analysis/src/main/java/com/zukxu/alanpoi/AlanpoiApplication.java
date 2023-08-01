package com.zukxu.alanpoi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 11:24:21
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zukxu.alanpoi.mapper"})
public class AlanpoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanpoiApplication.class, args);
    }
}
