package com.zukxu.test.others;

import java.time.LocalDate;

/**
 * <p>
 * LocalDateTime 测试
 * </p>
 *
 * @author xupu
 * @since 2022/8/26 10:37:14
 */
public class TestLocalDateTime {

    public static void main(String[] args) {
        testGet();
    }

    public static void testGet() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        System.out.println(now.getDayOfMonth());
        System.out.println(now.plusDays(-1).getDayOfMonth());
        System.out.println(now.getDayOfWeek().getValue());
        System.out.println(now.getMonth().getValue());
    }

}
