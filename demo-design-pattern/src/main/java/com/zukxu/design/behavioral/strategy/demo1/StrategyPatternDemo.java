package com.zukxu.design.behavioral.strategy.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:46:42
 */
public class StrategyPatternDemo {

    public static void main(String[] args) {
        Context context = new Context(new ConcreteStrategyA());
        context.executeStrategy();
        context.setStrategy(new ConcreteStrategyB());
        context.executeStrategy();
        context.setStrategy(new ConcreteStrategyC());
        context.executeStrategy();
    }

}
