package com.zukxu.demoliteflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("{com.zukxu.demoliteflow.mapper}")
@SpringBootApplication
public class DemoLiteFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoLiteFlowApplication.class, args);
    }

}
