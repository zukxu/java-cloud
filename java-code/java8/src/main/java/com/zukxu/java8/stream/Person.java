package com.zukxu.java8.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 员工类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/2 0002 10:29
 */
@Data
@AllArgsConstructor
public class Person {
    private String name;  // 姓名
    private int salary; // 薪资
    private int age; // 年龄
    private String sex; //性别
    private String area;  // 地区
}
