package com.zukxu.java8.test.Singleton;

/**
 * 饿汉式创建单例模式
 *
 * @author zukxu
 * CreateTime: 2021/7/7 0007 23:01
 */
public class HungrySingleton {
    private static HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton() {}

    public static HungrySingleton getSingleton() {
        return hungrySingleton;
    }

}
