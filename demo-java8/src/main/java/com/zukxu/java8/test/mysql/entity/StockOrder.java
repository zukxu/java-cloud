package com.zukxu.java8.test.mysql.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zukxu
 * CreateTime: 2021/5/11 0011 16:33
 */
@Data
@Accessors(chain = true)
public class StockOrder {
	private Integer id;
	private Integer sid;
	private String name;
	private LocalDateTime createTime;
}
