package com.zukxu;

import cn.xuyanwu.spring.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 参考：https://spring-file-storage.xuyanwu.cn/
 * </p>
 *
 * @author xupu
 * @since 2023/10/17 10:03:29
 */
@SpringBootApplication
@EnableFileStorage//开启文件上传
public class FileStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileStorageApplication.class, args);
    }
}