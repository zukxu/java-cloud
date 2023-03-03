package com.zukxu.design.creation.singleton;

/**
 * <p>
 * 懒汉式
 * <br/>
 * 单例实例在第一次使用时被创建。
 * 优点：延迟实例的创建，可以节省资源
 * 缺点：需要考虑线程安全问题。
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:26:36
 */
public class LazySingleton {
    private static volatile LazySingleton INSTANCE;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}
