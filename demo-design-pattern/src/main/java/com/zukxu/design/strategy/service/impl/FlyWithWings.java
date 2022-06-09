package com.zukxu.design.strategy.service.impl;

import com.zukxu.design.strategy.service.FlyBehavior;

/**
 * @author xupu
 * @Description 用翅膀飞翔
 * @Date 2021-09-13 14:33
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("使用翅膀飞翔");
    }
}
