package com.zukxu.test.proxyBean;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 11:53
 */
public class MyBean1 {
    @PostConstruct
    public void init() {
        System.out.println("MyBean1初始化了"+ LocalDateTime.now());
    }

    public void hello() {
        System.out.println("MyBean1 hello");
    }
}