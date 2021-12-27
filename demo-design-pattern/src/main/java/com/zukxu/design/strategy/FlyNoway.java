package com.zukxu.design.strategy;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-13 14:34
 */
public class FlyNoway implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("不能飞翔");
    }
}
