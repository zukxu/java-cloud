package com.zukxu.test.conditional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConditionalConfigTest {
    @Autowired(required = false) //一旦使用@Autowired那就默认代表当前Bean一定是已经存在的 如果为null，会报错required=false 的意思就是允许当前的Bean对象为null
    private People people;

    /**
     * 正常情况测试
     */
    @Test
    void test1() {
        System.out.println("= = = = = = = = = = = = = ");
        System.out.println("people = " + people);
        System.out.println("= = = = = = = = = = = = = ");
    }

    /**
     * 将配置类中的city对象注入进行注释
     */
    @Test
    void test2() {
        System.out.println("= = = = = = = = = = = = = ");
        System.out.println("people = " + people);
        System.out.println("= = = = = = = = = = = = = ");
    }

    /**
     * 仍然取消注入City,但是在People初始化方法上添加@ConditionalOnBean注解
     *
     * @ConditionalOnBean(City.class) 或者
     * @ConditionalOnBean(name = "city")
     */
    @Test
    void test3() {
        System.out.println("= = = = = = = = = = = = = ");
        System.out.println("people = " + people);
        System.out.println("= = = = = = = = = = = = = ");
    }

    /**
     * 测试@ConditionalOnMissingBean(City.class)
     */
    @Test
    void test4() {
        System.out.println("= = = = = = = = = = = = = ");
        System.out.println("people = " + people);
        System.out.println("= = = = = = = = = = = = = ");
    }

}