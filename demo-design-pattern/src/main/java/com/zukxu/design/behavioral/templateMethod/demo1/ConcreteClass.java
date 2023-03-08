package com.zukxu.design.behavioral.templateMethod.demo1;

/**
 * <p>
 * 创建一个具体子类，实现抽象方法。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:40:20
 */
public class ConcreteClass extends AbstractClass {

    void primitiveOperation1() {
        System.out.println("Primitive Operation 1 in ConcreteClass");
    }

    void primitiveOperation2() {
        System.out.println("Primitive Operation 2 in ConcreteClass");
    }

    public void hook() {
        System.out.println("Hook method in ConcreteClass");
    }

}