package com.zukxu.test.proxyBean;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 11:54
 */
public class MyBean2 {

    public MyBean1 myBean1;

    public MyBean2(MyBean1 myBean1){
        this.myBean1=myBean1;
    }

    @PostConstruct
    public void init(){
        System.out.println("MyBean2 初始化了"+ LocalDateTime.now());
    }

    public void hello(){
        System.out.println("MyBean2 hello");
    }
}