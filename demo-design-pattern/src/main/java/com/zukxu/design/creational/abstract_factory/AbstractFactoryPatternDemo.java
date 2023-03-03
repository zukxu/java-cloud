package com.zukxu.design.creational.abstract_factory;

import com.zukxu.design.creational.abstract_factory.service.AbstractFactory;
import com.zukxu.design.creational.abstract_factory.service.AbstractProductA;
import com.zukxu.design.creational.abstract_factory.service.AbstractProductB;

/**
 * <p>
 * AbstractFactory 是抽象工厂接口，定义了工厂可以生产哪些产品。
 * ConcreteFactoryA 和 ConcreteFactoryB 是具体工厂实现类，分别负责生产不同的产品。
 * ProductA 和 ProductB 是抽象产品接口，定义了产品的操作。
 * ProductAImplA、ProductAImplB、ProductBImplA 和 ProductBImplB 是具体产品实现类，分别实现了 ProductA 和 ProductB 的操作。
 * <p>
 * 在测试代码中，我们通过创建不同的工厂实例和调用它们的方法来创建不同的产品
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:56:19
 */
// 测试代码
public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {
        // 创建具体工厂实例
        AbstractFactory factoryA = new ConcreteFactoryA();
        AbstractFactory factoryB = new ConcreteFactoryB();

        // 工厂A生产产品A和产品B
        AbstractProductA productA1 = factoryA.createProductA();
        productA1.operationA();
        AbstractProductB productB1 = factoryA.createProductB();
        productB1.operationB();

        // 工厂B生产产品A和产品B
        AbstractProductA productA2 = factoryB.createProductA();
        productA2.operationA();
        AbstractProductB productB2 = factoryB.createProductB();
        productB2.operationB();
    }

}
