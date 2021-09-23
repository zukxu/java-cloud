package com.zukxu.dataway;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/13 0013 16:53
 */
@EnableHasor
@EnableHasorWeb
@SpringBootApplication(scanBasePackages = {"com.zukxu.dataway"})
public class DataWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataWayApplication.class);
	}
}
