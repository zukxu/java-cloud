package com.zukxu.design.creation.abstract_factory;

import com.zukxu.design.creation.abstract_factory.service.AbstractFactory;
import com.zukxu.design.creation.abstract_factory.service.AbstractProductA;
import com.zukxu.design.creation.abstract_factory.service.AbstractProductB;

/**
 * <p>
 * 抽象工厂实现类1
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:46:32
 */
public class ConcreteFactoryA implements AbstractFactory {

    @Override
    public AbstractProductA createProductA() {
        return new ProductAImplA();
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductBImplA();
    }

}