package com.zukxu.design.creation.prototype;

/**
 * <p>
 * 长方形
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:48:04
 */

public class Rectangle extends Shape {

    public Rectangle() {
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }

}