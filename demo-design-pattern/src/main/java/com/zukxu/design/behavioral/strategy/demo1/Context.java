package com.zukxu.design.behavioral.strategy.demo1;

/**
 * <p>
 * 包含策略的上下文类，该类可以接受一个策略对象，并将算法执行委托给该策略对象
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:45:50
 */
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.doOperation();
    }

}
