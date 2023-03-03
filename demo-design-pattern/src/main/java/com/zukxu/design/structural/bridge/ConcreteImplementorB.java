package com.zukxu.design.structural.bridge;

/**
 * <p>
 * 具体实现类B
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:14:14
 */
public class ConcreteImplementorB implements Implementor {

    @Override
    public void operationImpl() {
        System.out.println("Concrete Implementor B");
    }

}