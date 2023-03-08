package com.zukxu.design.behavioral.templateMethod.demo1;

/**
 * <p>
 * 定义算法的骨架，并声明一些抽象方法，这些抽象方法由子类实现
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:39:16
 */
public abstract class AbstractClass {

    // 模板方法
    public final void templateMethod() {
        primitiveOperation1();
        primitiveOperation2();
        concreteOperation();
        hook();
    }

    // 抽象方法，由子类实现
    abstract void primitiveOperation1();

    abstract void primitiveOperation2();

    // 具体方法，由抽象类实现
    public void concreteOperation() {
        System.out.println("Concrete operation in AbstractClass");
    }

    // 钩子方法，可由子类覆盖
    public void hook() {
    }

}

