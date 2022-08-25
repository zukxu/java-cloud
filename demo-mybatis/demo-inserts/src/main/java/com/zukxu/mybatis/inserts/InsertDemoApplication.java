package com.zukxu.mybatis.inserts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 9:49:44
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zukxu.mybatis.inserts.mapper"})
public class InsertDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsertDemoApplication.class, args);
    }
}
