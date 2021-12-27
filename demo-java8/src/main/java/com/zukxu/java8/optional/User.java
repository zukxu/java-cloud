package com.zukxu.java8.optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 09:38
 */
@Data
@NoArgsConstructor
public class User {

	private String id;
	private String name;
	private Integer age;
	private LocalDate birthday;

	public User(String name) {
		this.name = name;
	}
}
