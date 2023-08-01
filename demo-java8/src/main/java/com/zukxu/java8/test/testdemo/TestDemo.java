package com.zukxu.java8.test.testdemo;

/**
 * @author zukxu
 * CreateTime: 2021/7/8 0008 15:00
 */
public class TestDemo {
    long width;

    public TestDemo(long l) {
        width = l;
    }

    public static void main(String arg[]) {
        TestDemo a, b, c;
        a = new TestDemo(42L);
        b = new TestDemo(42L);
        c = b;
        long s = 42L;

        System.out.println(a == b);
        // System.out.println(s == a);
        System.out.println(b == c);
        System.out.println(a.equals(s));
    }
}
