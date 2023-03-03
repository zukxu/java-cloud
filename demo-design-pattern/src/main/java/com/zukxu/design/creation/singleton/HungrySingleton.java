package com.zukxu.design.creation.singleton;


/**
 * <p>
 *  饿汉式
 *  <br/>
 *  在这种实现方式中，单例实例在类加载时就被创建。
 *  优点:简单易用，线程安全，
 *  缺点:可能会浪费资源，因为实例在程序启动时就被创建。
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:21
 */
public class HungrySingleton {
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    private HungrySingleton() {}

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }
}

