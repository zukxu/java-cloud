package com.zukxu.java8.lambda.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-17 11:22
 */
@Data
@Accessors(chain = true)
public class Student {
    private String name;
    private Integer age;
    private Integer height;
    private List<HobbyEnum> hobby;

    public Student() {
    }

    public Student(String name, Integer age, Integer height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }
}