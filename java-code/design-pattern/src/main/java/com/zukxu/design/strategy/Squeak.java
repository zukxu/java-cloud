package com.zukxu.design.strategy;

/**
 * @author xupu
 * @Description 橡皮鸭叫
 * @Date 2021-09-13 14:35
 */
public class Squeak implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("橡皮鸭叫");
    }
}
