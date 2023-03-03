package com.zukxu.design.creation.prototype;

/**
 * <p>
 * Shape 是抽象类，代表图形对象。
 * Circle、Rectangle、Square 都是 Shape 的子类，分别表示圆、矩形、正方形。
 * ShapeCache 是缓存类，用于缓存各种图形对象。
 * PrototypePatternDemo 是演示类，用于展示如何使用原型模式。
 * <p>
 * 具体地，Shape 类实现了 Cloneable 接口，并重写了 clone() 方法。每个具体的图形对象都可以
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 16:54:05
 */
public class PrototypePatternDemo {

    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape clonedShape1 = ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape1.getType());

        Shape clonedShape2 = ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
    }

}