package com.zukxu.java8.test.testdemo;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/15 0015 16:07
 */
public class StringTest {

    public static void main(String[] args) {
        testStr1();
    }

    public static void testStr1() {
        String path = "E:/temp/r_pan root/测试1/cxdj.keystore";
        String name = "cxdj.keystore";
        String dirName = path.substring(0, path.indexOf(name) - 1);
        System.out.println(dirName);

    }
}

