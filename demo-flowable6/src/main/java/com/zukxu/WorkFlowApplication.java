package com.zukxu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xupu
 * @Date 2021-11-16 18:55
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zukxu.flowable.mapper"})
public class WorkFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowApplication.class, args);
    }

}
