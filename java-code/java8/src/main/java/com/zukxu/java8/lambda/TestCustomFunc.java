package com.zukxu.java8.lambda;

/**
 * 测试自定义lambda函数式接口
 *
 * @author zukxu
 * CreteTime 2021/7/17 0017 00:34
 */
public class TestCustomFunc {
	public static void main(String[] args) {
		testCustom(() -> "我是一个自定义函数式接口");
	}

	public static void testCustom(Worker worker) {
		System.out.println(worker.work());
	}

	public interface Worker {
		String work();
	}
}
