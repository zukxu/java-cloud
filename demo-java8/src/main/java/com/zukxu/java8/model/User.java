package com.zukxu.java8.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@AllArgsConstructor
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
