package com.zukxu.java8.test.synchronize;

/**
 * @author zukxu
 * CreateTime: 2021/6/25 0025 20:54
 */
public class SyncTest1 {
	public static int account = 0;

	public static void main(String[] args) {
		//定义两个线程，分别调用存储和查询的方法
	}

	public static void store(int money) {
		account = account + money;
	}

	public static void query() {
		System.out.println("你的账户余额为：" + account);
	}
}
