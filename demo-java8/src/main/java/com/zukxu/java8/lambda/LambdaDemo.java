package com.zukxu.java8.lambda;

import com.zukxu.java8.lambda.model.Student;

import java.math.BigDecimal;
import java.util.function.*;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-17 11:24
 */
public class LambdaDemo {
    public static void main(String[] args) {
        Student student = new Student().setName("小明").setAge(23).setHeight(180);

        //	判断真假 Predicate
        Predicate<Integer> pred = x -> x > 185;
        System.out.println(pred.test(student.getHeight()));

        //	消费消息
        Consumer<String> consumer = System.out::println;
        consumer.accept("我命由我不由天");

        //	转换功能
        Function<Student, String> function = Student::getName;
        String name = function.apply(student);
        System.out.println(name);

        //	生产消息
        Supplier<Integer> supplier = () -> Integer.valueOf(BigDecimal.TEN.toString());
        System.out.println(supplier.get());

        //  一元操作
        UnaryOperator<Boolean> unaryOperator = u -> !u;
        Boolean apply = unaryOperator.apply(false);
        System.out.println(apply);

        //	二元操作
        BinaryOperator<Integer> binaryOperator = (x, y) -> x * y + 2;
        Integer binary = binaryOperator.apply(5, 15);
        System.out.println(binary);
    }
}
