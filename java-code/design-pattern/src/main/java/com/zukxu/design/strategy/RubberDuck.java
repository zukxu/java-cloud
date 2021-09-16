package com.zukxu.design.strategy;

/**
 * @author xupu
 * @Description 橡皮鸭
 * @Date 2021-09-13 14:39
 */
public class RubberDuck {
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
