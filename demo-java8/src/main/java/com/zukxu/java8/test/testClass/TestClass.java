package com.zukxu.java8.test.testClass;

/**
 * @author zukxu
 * CreateTime: 2021/7/7 0007 20:59
 */
public class TestClass {
    private static final String MESSAGE = "taobao";

    static {
        System.out.println("静态代码块 TestClass ");
    }

    {
        System.out.println("构造代码块 TestClass");
    }

    public TestClass() {
        System.out.println("构造函数 TestClass");
    }

    public static void main(String[] args) {
        new A();
        String a = "tao" + "bao";
        String b = "tao";
        String c = "bao";
        System.out.println(a == MESSAGE);
        System.out.println((b + c) == MESSAGE);
    }
}
