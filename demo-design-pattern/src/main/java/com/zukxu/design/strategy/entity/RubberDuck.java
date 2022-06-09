package com.zukxu.design.strategy.entity;

import com.zukxu.design.strategy.service.impl.FlyNoway;
import com.zukxu.design.strategy.service.impl.Squeak;

/**
 * @author xupu
 * @Description 橡皮鸭
 * @Date 2021-09-13 14:39
 */
public class RubberDuck extends Duck {
    public static void main(String[] args) {
        RubberDuck rubberDuck = new RubberDuck();
        rubberDuck.quack();
        rubberDuck.fly();
        rubberDuck.display();
    }

    public RubberDuck() {
        flyBehavior = new FlyNoway();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("黑白橡皮颜色");
    }
}
