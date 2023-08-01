package com.zukxu.java8.test.mysql.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zukxu
 * CreateTime: 2021/5/11 0011 16:33
 */
@Data
@Accessors(chain = true)
public class Stock {
    private Integer id;
    private String name;
    private Integer count;
    private Integer sale;
    private Integer version;
}
