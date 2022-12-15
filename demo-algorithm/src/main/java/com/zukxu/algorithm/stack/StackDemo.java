package com.zukxu.algorithm.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * 栈demo
 *
 * @author zukxu
 * CreateTime: 2021/5/7 0007 22:35
 */
public class StackDemo {

    public static void main(String[] args) {
        int[] myArray = { 23, 93, 56, 92, 39 };
        reverseArrayByStack(myArray);

    }

    /** 使用Stack反转数组 */
    private static void reverseArrayByStack(int[] myArray) {
        Stack<Integer> stack = new Stack<>();
        for(int j : myArray) {
            stack.push(j);
        }

        int[] reverseArray = new int[myArray.length];
        int size = stack.size();
        for(int i = 0; i < size; i++) {
            reverseArray[i] = stack.pop();
        }

        System.out.println("Reversed array is :" + Arrays.toString(reverseArray));

    }

}
