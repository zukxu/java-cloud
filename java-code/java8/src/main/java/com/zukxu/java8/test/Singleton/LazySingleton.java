package com.zukxu.java8.test.Singleton;

/**
 * 懒汉式创建单例模式
 *
 * @author zukxu
 * CreateTime: 2021/7/7 0007 23:01
 */
public class LazySingleton {
	//类初始化时不初始化该对象
	private static LazySingleton lazySingleton;

	private LazySingleton() {}

	//方法同步，调用效率低
	public static synchronized LazySingleton getSingleton() {
		if (lazySingleton == null) {
			lazySingleton = new LazySingleton();
		}
		return lazySingleton;
	}

}
