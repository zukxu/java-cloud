package com.zukxu.test.time;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author xupu
 * @Description
 * @Date 2021-10-14 10:04
 */
public class TestLocalDateTime {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireIn = now.plusMinutes(2);
        testCha(expireIn, now);
    }

    private static void testCha(LocalDateTime expireIn, LocalDateTime now) {
        Duration duration = Duration.between(now, expireIn);
        System.out.println("day" + duration.toDays());
        System.out.println("hour" + duration.toHours());
        System.out.println("minutes" + duration.toMinutes());
        System.out.println("mills" + duration.toMillis());
        System.out.println("nanos" + duration.toNanos());
    }
}
