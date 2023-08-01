package com.zukxu.algorithm.array;

import java.util.ArrayList;

/**
 * <p>
 * 数组与矩阵相关算法题
 * </p>
 *
 * @author xupu
 * @since 2023/7/22 15:20:59
 */
public class ArraysAndMatrices {
    public static void main(String[] args) {
        //duplicateNum();
        //findInArrays();
        replaceBlank("A B CD");
    }

    /**
     * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     * <P>
     * Input:
     * {2, 3, 1, 0, 2, 5}
     * <br>
     * Output:
     * 2
     * </P>
     * <p></p>
     * 要求时间复杂度 O(N)，空间复杂度 O(1)。因此不能使用排序的方法，也不能使用额外的标记数组。     *
     * 对于这种数组元素在 [0, n-1] 范围内的问题，可以将值为 i 的元素调整到第 i 个位置上进行求解。在调整过程中，如果第 i 位置上已经有一个值为 i 的元素，就可以知道 i 值重复。
     * 以 (2, 3, 1, 0, 2, 5) 为例，遍历到位置 4 时，该位置上的数为 2，但是第 2 个位置上已经有一个 2 的值了，因此可以知道 2 重复：
     * </p>
     */
    public static void duplicateNum() {
        int[] nums = new int[]{2, 3, 1, 0, 2, 5};
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    System.out.println(nums[i]);
                    return;
                }
                swap(nums, i, nums[i]);
            }
            swap(nums, i, nums[i]);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * 二维数组中的查找
     * 给定一个二维数组，其每一行从左到右递增排序，从上到下也是递增排序。给定一个数，判断这个数是否在该二维数组中。
     * <p>
     * Consider the following matrix:
     * [
     * [1,   4,  7, 11, 15],
     * [2,   5,  8, 12, 19],
     * [3,   6,  9, 16, 22],
     * [10, 13, 14, 17, 24],
     * [18, 21, 23, 26, 30]
     * ]
     * <p>
     * Given target = 5, return true.
     * Given target = 20, return false.
     * </p>
     * <p>
     * 要求时间复杂度 O(M + N)，空间复杂度 O(1)。其中 M 为行数，N 为 列数。
     * 解题思路：
     * 该二维数组中的一个数，小于它的数一定在其左边，大于它的数一定在其下边。因此，从右上角开始查找，
     * 就可以根据 target 和当前元素的大小关系来快速地缩小查找区间，每次减少一行或者一列的元素。当前元素的查找区间为左下角的所有元素。
     * </p>
     */
    public static void findInArrays() {
        int[][] matrix = new int[5][5];
        int value = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = value;
                value += 3;
            }
        }
        System.out.println("输出二维数组：");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        int target = 7;
        System.out.println(isNumExistInArrays(target, matrix));
        /*//自己编写的
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] > target) {
                j--;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                System.out.printf("待查找的数：%d\n所在位置为：%d %d", target, i, j);
                return;
            }
        }
        System.out.printf("待查找的数：%d,不在当前二维数组中", target);*/
    }

    /**
     * 标准答案
     *
     * @param target 待查找的数
     * @param matrix 矩阵
     * @return bool
     */
    public static boolean isNumExistInArrays(int target, int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int rows = matrix.length, cols = matrix[0].length;
        int r = 0, c = cols - 1; // 从右上角开始
        while (r <= rows - 1 && c >= 0) {
            if (target == matrix[r][c]) {
                System.out.printf("待查找的数:%d, 所在位置为： matrix[%d][%d]: %n", target, r, c);
                return true;
            } else if (target > matrix[r][c])
                r++;
            else
                c--;
        }
        return false;
    }

    /**
     * 替换字符串中的空格为%20
     *
     * @param str 字符串
     * @return 替换后的字符串
     */
    public static String replaceBlank(String str) {
        //第一种方法
        //return str.replaceAll(" ", "%20");
        //第二种方法
        /*StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();*/
        return str;
    }

    /*算法解析：标准答案*/
    public String replaceSpace(StringBuffer str) {
        int P1 = str.length() - 1;
        for (int i = 0; i <= P1; i++)
            if (str.charAt(i) == ' ')
                str.append("  ");

        int P2 = str.length() - 1;
        while (P1 >= 0 && P2 > P1) {
            char c = str.charAt(P1--);
            if (c == ' ') {
                str.setCharAt(P2--, '0');
                str.setCharAt(P2--, '2');
                str.setCharAt(P2--, '%');
            } else {
                str.setCharAt(P2--, c);
            }
        }
        return str.toString();
    }

    /**
     * 顺时针打印矩阵
     *
     * @param matrix 矩阵
     * @return str
     * <p>
     * 一层一层从外到里打印，观察可知每一层打印都有相同的处理步骤，唯一不同的是上下左右的边界不同了。因此使用四个变量 r1, r2, c1, c2 分别存储上下左右边界值，从而定义当前最外层。
     * 打印当前最外层的顺序：从左到右打印最上一行->从上到下打印最右一行->从右到左打印最下一行->从下到上打印最左一行。应当注意只有在 r1 != r2 时才打印最下一行，
     * 也就是在当前最外层的行数大于 1 时才打印最下一行，这是因为当前最外层只有一行时，继续打印最下一行，会导致重复打印。打印最左一行也要做同样处理。
     * </p>
     */
    public ArrayList<Integer> printMatrixClockWise(int[][] matrix) {
        ArrayList<Integer> ret = new ArrayList<>();
        int r1 = 0, r2 = matrix.length - 1, c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) {
            // 上
            for (int i = c1; i <= c2; i++)
                ret.add(matrix[r1][i]);
            // 右
            for (int i = r1 + 1; i <= r2; i++)
                ret.add(matrix[i][c2]);
            if (r1 != r2)
                // 下
                for (int i = c2 - 1; i >= c1; i--)
                    ret.add(matrix[r2][i]);
            if (c1 != c2)
                // 左
                for (int i = r2 - 1; i > r1; i--)
                    ret.add(matrix[i][c1]);
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ret;
    }
}
