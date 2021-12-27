package com.zukxu.design.strategy;

/**
 * @author xupu
 * @Description 抽象鸭子实体
 * @Date 2021-09-13 14:37
 */
public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    public void fly() {
        flyBehavior.fly();
    }

    public void quack() {
        quackBehavior.quack();
    }

    public abstract void display();
}
