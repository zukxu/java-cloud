package com.zukxu.design.structural.flyweight;

/**
 * <p>
 * 示例
 * 定义享元接口（Flyweight），包含一个操作方法，该方法接收一个外部状态作为参数。
 * 创建享元实现类（ConcreteFlyweight），实现享元接口，同时包含内部状态。
 * 创建享元工厂类（FlyweightFactory），用于创建和管理享元对象，保证相同的享元对象只被创建一次，通常使用HashMap来存储和管理享元对象。
 * 客户端（Client）通过享元工厂类获取享元对象，并调用其操作方法，传入外部状态。
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:52:56
 */
public class FlyweightPatternDemo {

    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight flyweight1 = factory.getFlyweight(1);
        Flyweight flyweight2 = factory.getFlyweight(2);
        flyweight1.operation(10);
        flyweight2.operation(20);
    }

}
