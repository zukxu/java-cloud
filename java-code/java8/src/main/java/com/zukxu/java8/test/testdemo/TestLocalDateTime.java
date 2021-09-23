package com.zukxu.java8.test.testdemo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/28 0028 22:23
 */
public class TestLocalDateTime {
	public static void main(String[] args) {
		// testLocalDateTime1();
		testLocalDateTime2();

	}

	public static void testLocalDateTime1() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

		String strTime = "2021-01-23 10:29:28.248";
		LocalDateTime now = LocalDateTime.now();
		LocalDate date = LocalDate.from(now);
		System.out.println(date);
		String overTime = String.valueOf(now.plusMinutes(30)).replace("T", " ");
		String ext4 = df.format(now.plusMinutes(40));

		System.out.println(overTime);
		System.out.println(ext4);

		System.out.println(LocalDateTime.parse(overTime, df));
		System.out.println(LocalDateTime.parse(ext4, df));


	}

	public static void testLocalDateTime2() {
		LocalDate localDate = LocalDate.now();
		LocalDateTime localDateTime1 = localDate.atStartOfDay();
		LocalDateTime localDateTime2 = localDate.atTime(0, 0, 0);
		LocalDateTime localDateTime3 = localDate.atTime(23, 59, 59);
		LocalDateTime localDateTime4 = localDate.atTime(LocalTime.now());
		LocalDateTime localDateTime5 = localDate.atTime(LocalTime.now());


		System.out.println(localDate);
		System.out.println(localDateTime1);
		System.out.println(localDateTime2);
		System.out.println(localDateTime3);
		System.out.println(localDateTime4);

	}
}
