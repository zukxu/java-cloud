package com.zukxu.mybatis.flex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/7/26 14:46:38
 */
@SpringBootApplication
@MapperScan("com.zukxu.mybatis.flex.mapper")
public class MybatisFlexApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisFlexApplication.class, args);
    }
}
