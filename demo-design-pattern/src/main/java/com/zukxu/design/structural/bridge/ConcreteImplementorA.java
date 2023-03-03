package com.zukxu.design.structural.bridge;

/**
 * <p>
 * 具体实现类A
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:14:01
 */
public class ConcreteImplementorA implements Implementor {

    @Override
    public void operationImpl() {
        System.out.println("Concrete Implementor A");
    }

}
