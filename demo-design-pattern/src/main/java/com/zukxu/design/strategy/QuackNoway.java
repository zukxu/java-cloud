package com.zukxu.design.strategy;

/**
 * @author xupu
 * @Description 不会叫
 * @Date 2021-09-13 14:35
 */
public class QuackNoway implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("不会叫");
    }
}
