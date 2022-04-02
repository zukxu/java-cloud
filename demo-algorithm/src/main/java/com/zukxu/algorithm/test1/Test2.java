package com.zukxu.algorithm.test1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 16:03
 */
public class Test2 {
    /**
     * 第一行一个正整数n <=987654321，表示小朋友的个数．
     * 接下来n行，每行一个整数ai,表示第i个小朋友得到的梨子的颗数
     * <p>
     * 有n个小朋友坐成一圈，每人有ai个梨子。每人只向两侧的人传递梨子。每人每次传递一个梨子代价为 1
     * <p>
     * 求使所有人获得均等梨子的最小代价。
     * 样例：
     * n=4
     * 1
     * 2
     * 5
     * 4
     * <p>
     * 输出：4
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("正整数n");
        int n = sc.nextInt();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("共" + n + "个数，请输入第【" + (i + 1) + "】个数");
            num[i] = sc.nextInt();
        }

        int avg = (int) (Arrays.stream(num).count() / n);
        System.out.println("平均数为："+avg);
    }
}
