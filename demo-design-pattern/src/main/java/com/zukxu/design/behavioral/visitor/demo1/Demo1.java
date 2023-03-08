package com.zukxu.design.behavioral.visitor.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:56:10
 */
// 定义图形接口
interface Shape {

    void accept(Visitor visitor);

}

// 定义访问者接口
interface Visitor {

    void visit(Circle circle);

    void visit(Rectangle rectangle);

    void visit(Triangle triangle);

}

// 实现圆形类
class Circle implements Shape {

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}

// 实现矩形类
class Rectangle implements Shape {

    private double length;

    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}

// 实现三角形类
class Triangle implements Shape {

    private double a;

    private double b;

    private double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}

// 对象结构类
class ShapeCollection {

    private List<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public void accept(Visitor visitor) {
        for(Shape shape : shapes) {
            shape.accept(visitor);
        }
    }

}

// 实现计算面积的访问者
class AreaVisitor implements Visitor {

    private double totalArea = 0;

    @Override
    public void visit(Circle circle) {
        double area = Math.PI * circle.getRadius() * circle.getRadius();
        System.out.println("Circle: area = " + area);
        totalArea += area;
    }

    @Override
    public void visit(Rectangle rectangle) {
        double area = rectangle.getLength() * rectangle.getWidth();
        System.out.println("Rectangle: area = " + area);
        totalArea += area;
    }

    @Override
    public void visit(Triangle triangle) {
        double s = (triangle.getA() + triangle.getB() + triangle.getC()) / 2;
        double area = Math.sqrt(s * (s - triangle.getA()) * (s - triangle.getB()) * (s - triangle.getC()));
        System.out.println("Triangle: area = " + area);
        totalArea += area;
    }

    public double getTotalArea() {
        return totalArea;
    }

}

// 实现计算周长的访问者
class PerimeterVisitor implements Visitor {

    private double totalPerimeter = 0;

    @Override
    public void visit(Circle circle) {
        double perimeter = 2 * Math.PI * circle.getRadius();
        System.out.println("Circle: Perimeter = " + perimeter);
        totalPerimeter += perimeter;
    }

    @Override
    public void visit(Rectangle rectangle) {
        double perimeter = (rectangle.getLength() + rectangle.getWidth()) * 2;
        System.out.println("Rectangle: Perimeter = " + perimeter);
        totalPerimeter += perimeter;
    }

    @Override
    public void visit(Triangle triangle) {
        double perimeter = triangle.getA() + triangle.getB() + triangle.getC();
        System.out.println("Triangle: Perimeter = " + perimeter);
        totalPerimeter += perimeter;
    }

    public double getTotalArea() {
        return totalPerimeter;
    }
}

public class Demo1 {

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(3));
        shapes.add(new Rectangle(4, 5));
        shapes.add(new Triangle(3, 4, 5));

        Visitor areaVisitor = new AreaVisitor();
        Visitor perimeterVisitor = new PerimeterVisitor();
        for(Shape shape : shapes) {
            shape.accept(areaVisitor);
            shape.accept(perimeterVisitor);
        }
    }
}