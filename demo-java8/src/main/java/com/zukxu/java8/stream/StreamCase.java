package com.zukxu.java8.stream;

import com.alibaba.fastjson.JSON;
import com.zukxu.java8.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
		log.info(string);
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
		stringList = stringList.stream().distinct().collect(Collectors.toList());
		log.info("去重后：");
		for (String s : stringList) {
			log.info(s);
		}
	}

	public static void testListObjectDistinct() {
		// 1. 对于 Student 列表去重
		List<Student> studentList = getStudentList();
		log.info("去重前：{}", JSON.toJSONString(studentList));
		studentList = studentList.stream().distinct().collect(Collectors.toList());
		log.info("去重后：{}", JSON.toJSONString(studentList));
	}


	public static void testFilterDistinct() {
		List<Student> studentList = getStudentList();

		log.info("去重前: {}", JSON.toJSONString(studentList));
		studentList = studentList.stream().distinct().collect(Collectors.toList());
		log.info("distinct去重后:");
		log.info("distinct去重后: {}", JSON.toJSONString(studentList));
		// 这里我们将 distinctByKey() 方法作为 filter() 的参数，过滤掉那些不能加入到 set 的元素
		studentList = studentList.stream().filter(distinctByKey(Student::getAge)).collect(Collectors.toList());
		log.info("根据名字去重后: {}", JSON.toJSONString(studentList));
	}

	/**
	 * 判断key是否可以加入set
	 *
	 * @param keyExtractor
	 * @param <T>
	 * @return
	 */
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	private static List<Student> getStudentList() {
		ArrayList<Student> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new Student("小米" + i % 3, i % 3 == 0 ? "男" : "女", (int)99 / (i % 3 + 2)));
		}
		return list;
	}
}
