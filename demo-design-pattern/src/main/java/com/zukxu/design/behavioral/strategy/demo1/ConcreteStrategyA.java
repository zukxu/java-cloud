package com.zukxu.design.behavioral.strategy.demo1;

/**
 * <p>
 * 具体策略A
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:44:20
 */
public class ConcreteStrategyA implements Strategy {

    public void doOperation() {
        System.out.println("ConcreteStrategyA.doOperation()");
    }

}
