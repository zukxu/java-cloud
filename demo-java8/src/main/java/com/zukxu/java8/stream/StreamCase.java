package com.zukxu.java8.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 调试Stream
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/9 0009 11:50
 */
public class StreamCase {
	private static final Logger log = LoggerFactory.getLogger(StreamCase.class);

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
		System.log.infoln(string);
	}

	/**
	 * 去重案例
	 */
	public static void testListStringDistinct() {
		// 1. 对于 String 列表去重
		List<String> stringList = new ArrayList<String>() {{
			add("A");
			add("A");
			add("B");
			add("B");
			add("C");
			add("D");
		}};
		log.info("去重前：");
		for (String s : stringList) {
			log.info(s);
		}
		System.out.println();
		stringList = stringList.stream().distinct().collect(Collectors.toList());
		log.info("去重后：");
		for (String s : stringList) {
			log.info(s);
		}
		System.out.println();
	}

	public static void testListObjectDistinct() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 1. 对于 Student 列表去重
		List<Student> studentList = getStudentList();
		out.print("去重前：");
		out.println(objectMapper.writeValueAsString(studentList));
		studentList = studentList.stream().distinct().collect(Collectors.toList());
		out.print("去重后：");
		out.println(objectMapper.writeValueAsString(studentList));
	}

	public static void testStringDistinct() {}

	public static void testStringDistinct() {}
}
