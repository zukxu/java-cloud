package com.zukxu.design.creational.prototype;

/**
 * <p>
 * 正方形
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:48:04
 */

public class Square extends Shape {

    public Square() {
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }

}