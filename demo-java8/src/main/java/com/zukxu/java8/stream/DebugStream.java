package com.zukxu.java8.stream;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * 调试Stream
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/9 0009 11:50
 */
public class DebugStream {
	public static void main(String[] args) {
		// testStream();
		testOptional();
	}
	public static void testStream() {
		Stream.of(100, 200, 300, 400, 500)
			  .mapToLong(e -> e * 10)
			  .filter(e -> e > 2000)
			  .forEach(System.out::println);
	}
	public static void testOptional() {
		String string = Optional.of("hi,")
								.map(e -> e + "Java")
								.map(e -> e + "技术")
								.map(e -> e + "小白").get();
		System.out.println(string);
	}
}
