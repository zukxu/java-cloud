package com.zukxu.java8.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-17 11:22
 */
@Data
@Accessors(chain = true)
public class Student implements Cloneable, Serializable {
    private String id;
    private String name;
    private String sex;
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

    public Student(String id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public Student clone() {
        Student student = null;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return student;
    }
}