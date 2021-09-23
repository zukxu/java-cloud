package com.zukxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zukxu
 */

@EnableScheduling
@SpringBootApplication
public class MinioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinioApplication.class, args);
	}

}
