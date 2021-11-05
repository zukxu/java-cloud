package com.zukxu.java8.lambda.reference;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.function.Function;

/**
 * 数组引用
 *
 * @author xupu
 * @date 2021/11/4 21:43:15
 */
public class ArrayReference {

    public static void main(String[] args) {
        Function<Integer, String[]> func1 = x -> new String[x];
        String[] apply = func1.apply(10);

        Function<Integer, String[]> func2 = String[]::new;
        func2.apply(10);
    }

}
