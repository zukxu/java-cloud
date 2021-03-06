package com.zukxu.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-30 11:09
 */
@SpringBootApplication
@MapperScan({"com.zukxu.**.mapper"})
public class ExcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
        System.out.println("启动完毕！！！");
    }
}
