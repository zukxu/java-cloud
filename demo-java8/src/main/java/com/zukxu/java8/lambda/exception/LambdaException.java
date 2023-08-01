package com.zukxu.java8.lambda.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;

/**
 * lambda中有异常
 * 任何的函数式接口都不能抛出受检异常-》 非RuntimeException
 *
 * @author xupu
 * @date 2021/11/4 21:48:02
 */
public class LambdaException {

    public static void main(String[] args) {
        Function<BufferedReader, String> func1 = (BufferedReader reader) -> {
            try {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

}
