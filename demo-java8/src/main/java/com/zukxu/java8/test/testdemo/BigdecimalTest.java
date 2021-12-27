package com.zukxu.java8.test.testdemo;

import java.math.BigDecimal;

/**
 * <p>
 * 测试BigDecimal
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/31 0031 09:50
 */
public class BigdecimalTest {
	public static void main(String[] args) {
		// create 2 BigDecimal Objects
		BigDecimal bg1, bg2;
		long amount1 = 1L;
		long amount2 = 100L;
		bg1 = new BigDecimal(String.valueOf((double) amount1 / 100));
		bg2 = BigDecimal.valueOf((double) amount2 / 100).setScale(2, 0);
		// set scale of bg1 to 2 in bg2
		// 0 specifies ROUND_UP
		String s = String.valueOf(bg2);

		String str = bg1 + " after changing the scale to 2 and rounding is " +bg2;

		// print bg2 value
		System.out.println(str);
		System.out.println(s);

	}
}
