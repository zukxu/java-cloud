package com.zukxu;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xupu
 * @Date 2021-11-05 15:25
 */
@SpringBootApplication
public class SaTokenDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenDemoApplication.class, args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }

}
