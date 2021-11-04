package com.zukxu.java8.lambda.reference;

import com.zukxu.java8.lambda.model.Project;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * lambda 方法引用
 * 1、指向静态方法的方法引用
 * 2、指向现有对象的实例方法的方法引用
 *
 * @author xupu
 * @date 2021/11/4 21:26:14
 */
public class MethodReference {

    public static List<Integer> findNumbers(List<Integer> numbers, Predicate<Integer> predicate) {
        List<Integer> integerList = numbers.stream().filter(predicate).collect(Collectors.toList());
        return integerList;
    }

    public static boolean multipleOf3(Integer number) {
        return (number % 3) == 0;
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9, 11, 15, 18);
        List<Integer> list = findNumbers(numbers, MethodReference::multipleOf3);
        System.out.println(list.contains(3));
        Project project = Project.builder().name("blade").build();

        // :: 引用操作符  实例方法的引用 Project::getName
        long count = Stream.of(project).map(Project::getName).count();
        System.out.println(count);
    }

}
