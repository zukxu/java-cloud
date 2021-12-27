package com.zukxu.validator.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:33
 */
@Data
public class Demo2 {
	@Length(min = 5, max = 17, message = "length长度在[5,17]之间")
	private String length;

	/**
	 * Size 不能验证Integer，适用于String, Collection, Map and arrays
	 */
	@Size(min = 1, max = 3, message = "size在[1,3]之间")
	private String age;

	@Range(min = 150, max = 250, message = "range在[150,250]之间")
	private int high;

	@Size(min = 3, max = 5, message = "list的Size在[3,5]")
	private List<String> list;

	@Length(min = 5, max = 17, message = "length长度在[5,17]之间")
	private String extField;

}
