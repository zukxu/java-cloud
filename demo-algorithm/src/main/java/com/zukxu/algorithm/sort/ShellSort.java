package com.zukxu.algorithm.sort;

import java.util.Arrays;

/**
 * @author xupu
 * @Description 希尔算法
 * @Date 2021-09-16 17:42
 */
public class ShellSort {

    /**
     * 对于希尔排序算法，我们更加倾向于将其进行分组，然后逐组进行处理
     */
    public static void main(String[] args) {
        int[] array = {15, 9, 7, 8, 20, -1, 4};
        System.out.println("初始化数组：" + Arrays.toString(array));
        changeSort(array);
        //moveSort(array);
        System.out.println("排序后的数组：" + Arrays.toString(array));
    }

    /**
     * 采用交换法进行排序
     *
     * @param arr 数组
     */
    private static void changeSort(int[] arr) {
        // 获取数组长度
        int length = arr.length;
        // 增量gap，并逐步缩小增量
        for (int count = 1, gap = length / 2; gap > 0; gap /= 2, count++) {
            // 从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < length; i++) {
                int j = i;
                // 在同一组内，对比前一个数，如果前一个数比当前数大，就交换它们的位置
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    // 插入排序采用交换法
                    swap(arr, j, j - gap);
                    j -= gap;
                }
            }
            // 输出当前遍历次数和增量，以及排序后的数组
            System.out.println("第" + count + "次遍历,标量为" + gap);
            for (int i : arr) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }

    /**
     * 交换数组中两个元素的位置
     *
     * @param arr 数组
     * @param i   index1
     * @param j   index2
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 采用移动法进行排序
     *
     * @param arr 数组
     */
    private static void moveSort(int[] arr) {
        int length = arr.length;
        //增量gap，并逐步缩小增量
        for (int gap = length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动法
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
        }
    }

}
