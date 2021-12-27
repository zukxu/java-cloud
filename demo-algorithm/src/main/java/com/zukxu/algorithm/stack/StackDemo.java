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
        Stack<Integer> stack = new Stack<>();

        int[] myArray = {23, 93, 56, 92, 39};

        int size = myArray.length;

        for (int j : myArray) {
            stack.push(j);
        }

        int[] reverseArray = new int[size];

        for (int i = 0; i < stack.size(); i++) {

            reverseArray[i] = stack.pop();

        }

        System.out.println("Reversed array is ::" + Arrays.toString(reverseArray));

    }
}
