package com.zukxu.algorithm.array;

import java.util.*;

/**
 * <p>
 * 数组与矩阵相关算法题
 * </p>
 *
 * @author xupu
 * @since 2023/7/22 15:20:59
 */
public class ArraysAndMatrices {

    /**
     * 初始化矩阵
     *
     * @param rows 行
     * @param cols 列
     * @return 矩阵
     */
    private int[][] initMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];

        // 初始化矩阵
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(201) - 100; // 生成-100到100之间的随机整数
            }
        }

        // 打印矩阵
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        return matrix;
    }

    public static void main(String[] args) {
        ArraysAndMatrices matrices = new ArraysAndMatrices();
        //matrices.duplicateNum();
        //matrices.findInArrays();
        //matrices.replaceBlank();
        //matrices.printMatrixClockWise();
    }

    /**
     * 1、在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
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
    public void duplicateNum() {
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

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * 2、二维数组中的查找
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
    public void findInArrays() {
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

    private boolean isNumExistInArrays(int target, int[][] matrix) {
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
     * 3、替换字符串中的空格为%20
     */
    public String replaceBlank() {
        String str = "A B CD";
        //return replaceSpace1(str);
        return method3_2(new StringBuffer(str));
    }

    private String method3_1(String str) {
        //第一种方法
        //return str.replaceAll(" ", "%20");
        //第二种方法
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();

    }

    private String method3_2(StringBuffer str) {
        int len = str.length() - 1;
        for (int i = 0; i <= len; i++)
            if (str.charAt(i) == ' ')
                str.append("  ");

        int P2 = str.length() - 1;
        while (len >= 0 && P2 > len) {
            char c = str.charAt(len--);
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
     * 4、顺时针打印矩阵
     *
     * <p>
     * 一层一层从外到里打印，观察可知每一层打印都有相同的处理步骤，唯一不同的是上下左右的边界不同了。因此使用四个变量 r1, r2, c1, c2 分别存储上下左右边界值，从而定义当前最外层。
     * 打印当前最外层的顺序：从左到右打印最上一行->从上到下打印最右一行->从右到左打印最下一行->从下到上打印最左一行。应当注意只有在 r1 != r2 时才打印最下一行，
     * 也就是在当前最外层的行数大于 1 时才打印最下一行，这是因为当前最外层只有一行时，继续打印最下一行，会导致重复打印。打印最左一行也要做同样处理。
     * </p>
     */
    public ArrayList<Integer> printMatrixClockWise() {
        int[][] matrix = initMatrix(5, 5);
        ArrayList<Integer> ret = new ArrayList<>();
        int r1 = 0, r2 = matrix.length - 1, c1 = 0, c2 = matrix[0].length - 1;//定义上下左右的边界值
        while (r1 <= r2 && c1 <= c2) {
            // 上
            for (int i = c1; i <= c2; i++) {
                ret.add(matrix[r1][i]);
            }
            // 右
            for (int i = r1 + 1; i <= r2; i++) {
                ret.add(matrix[i][c2]);
            }
            if (r1 != r2) {
                // 下
                for (int i = c2 - 1; i >= c1; i--) {
                    ret.add(matrix[r2][i]);
                }
            }
            if (c1 != c2) {
                // 左
                for (int i = r2 - 1; i > r1; i--) {
                    ret.add(matrix[i][c1]);
                }
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return ret;
    }

    /**
     * 5、寻找字符
     * 在一个字符串中找到第一个只出现一次的字符，并返回它的位置。字符串只包含 ASCII 码字符。
     * Input: abacc
     * Output: b
     * <p>
     * 最直观的解法是使用 HashMap 对出现次数进行统计：字符做为 key，出现次数作为 value，遍历字符串每次都将 key 对应的 value 加 1。最后再遍历这个 HashMap 就可以找出出现次数为 1 的字符。
     * 考虑到要统计的字符范围有限，也可以使用整型数组代替 HashMap。ASCII 码只有 128 个字符，因此可以使用长度为 128 的整型数组来存储每个字符出现的次数
     * </p>
     */
    public int firstNotRepeatingChar() {
        String str = "abccba";
        //return method5_1(str);
        return method5_2(str);
    }

    //自己编写
    private int method5_1(String str) {
        char[] charArray = str.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : charArray) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        //遍历Map
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    //最优解法
    //考虑到只需要找到只出现一次的字符，那么需要统计的次数信息只有 0,1,更大，使用两个比特位就能存储这些信息。
    private int method5_2(String str) {
        BitSet bs1 = new BitSet(128);
        BitSet bs2 = new BitSet(128);
        for (char c : str.toCharArray()) {
            if (!bs1.get(c) && !bs2.get(c))
                bs1.set(c);     // 0 0 -> 0 1
            else if (bs1.get(c) && !bs2.get(c))
                bs2.set(c);     // 0 1 -> 1 1
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (bs1.get(c) && !bs2.get(c))  // 0 1
                return i;
        }
        return -1;
    }
}
