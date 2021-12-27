package com.zukxu.java8.test.testdemo.loop;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zukxu
 * CreateTime: 2021/6/22 0022 19:44
 */
@Data
@Accessors(chain = true)
public class Record {
	private Integer mid;
	private String title;
	private String url;
	private String img;
	private String description;
	private Boolean isHot;
}
