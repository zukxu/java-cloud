package com.zukxu.gencode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zukxu.gencode.**.mapper")
public class GenCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenCodeApplication.class, args);
    }

}
