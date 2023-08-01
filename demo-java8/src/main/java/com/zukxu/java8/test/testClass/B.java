package com.zukxu.java8.test.testClass;

/**
 * @author zukxu
 * CreateTime: 2021/7/7 0007 20:59
 */
public class B {
    static {
        System.out.println("静态代码块 B");
    }

    {
        System.out.println("构造代码块 B");
    }

    public B() {
        System.out.println("构造函数 B");
    }
}
