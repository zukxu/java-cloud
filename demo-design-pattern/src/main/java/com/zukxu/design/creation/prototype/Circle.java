package com.zukxu.design.creation.prototype;

/**
 * <p>
 * 圆形
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:48:04
 */

public class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }

}