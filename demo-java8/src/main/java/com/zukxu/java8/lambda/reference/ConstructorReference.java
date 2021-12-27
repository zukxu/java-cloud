package com.zukxu.java8.lambda.reference;

import java.util.function.Supplier;

/**
 * 构造器引用
 * <p>
 * 对于一个现有一个构造函数，我们可以i利用他的名臣和关键字new来创建它的一个引用 ClassName::new
 * 它的给你与指向静态方法的引用类似
 *
 * @author xupu
 * @date 2021/11/4 21:40:01
 */
public class ConstructorReference {

    public static void main(String[] args) {
        //    根据参数列表自动匹配构造器
        Supplier<ConstructorReference> sup = ConstructorReference::new;
        ConstructorReference constructorReference = sup.get();

    }

}
