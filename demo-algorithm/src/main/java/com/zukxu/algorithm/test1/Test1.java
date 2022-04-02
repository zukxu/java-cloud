package com.zukxu.algorithm.test1;

import java.util.Scanner;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 15:52
 */
public class Test1 {
    /**
     * 通通家的萨摩耶叼来了一堆木棒，第i种木棒的长度是2^i(2,4,8,16…)，萨摩耶想让通通把这些木棒尽可能多的拼成三角形。
     * <p>
     * 输入
     * 第一行一个整数n
     * <p>
     * 接下来一行n个整数，分别表示第i种木棒有多少根
     * <p>
     * <p>
     * 输出
     * 一个整数表示最多可以拼成多少个三角形。
     * <p>
     * 输入样例
     * 5
     * 1 2 2 2 2
     * <p>
     * 输出样例
     * 3
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("一个整数n");
        int n = scanner.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("共" + n + "个数，请输入第" + (i + 1) + "个数");
            int ti = scanner.nextInt();
            if ((ti & ti - 1) == 0) {
                num[i] = ti;
            } else {
                System.out.println("该数字不是2的次方数,请重新输入");
                i--;
            }
        }

        for (int i : num) {
            System.out.print(i+" ");
        }
    }
}
