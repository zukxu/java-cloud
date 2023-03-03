package com.zukxu.design.creational.factory;

import com.zukxu.design.creational.factory.service.Factory;
import com.zukxu.design.creational.factory.service.Product;

/**
 * <p>
 * 工厂模式测试
 * <br/>
 * 首先通过FactoryA和FactoryB分别创建了两个工厂对象。
 * 然后通过工厂对象分别创建了ProductA和ProductB对象，并执行了它们的doSomething方法。
 * <p>
 * 这个示例代码演示了如何通过工厂模式实现对象的创建和使用的解耦，增加了代码的灵活性和可维护性。
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:42:27
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        // 创建工厂A
        Factory factoryA = new FactoryA();
        // 生产产品A
        Product productA = factoryA.createProduct();
        // 执行产品A的方法
        productA.doSomething();

        // 创建工厂B
        Factory factoryB = new FactoryB();
        // 生产产品B
        Product productB = factoryB.createProduct();
        // 执行产品B的方法
        productB.doSomething();
    }

}
