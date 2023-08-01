package com.zukxu.java8.test.floatdemo;

/**
 * @author zukxu
 * CreateTime: 2021/7/1 0001 14:24
 */
public class FloatDemo {
    public static void main(String[] args) {
        System.out.println(1f == 0.9999999f);
        System.out.println(1f == 0.99999999f);

        float f = 1.1f;
        double d = (double) f;
        System.out.println(f);  // 打印：1.1
        System.out.println(d);  // 打印：100000023841858

        System.out.println(0.2 + 0.7); //打印：0.8999999999999999

        float f2 = 84552631f;
        for (int i = 0; i < 10; i++) {
            System.out.println(f2);
            f2++;
        }
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7
        // 8.4552632E7

    }

}
