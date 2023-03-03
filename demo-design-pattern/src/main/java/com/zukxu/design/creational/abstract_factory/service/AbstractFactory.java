package com.zukxu.design.creational.abstract_factory.service;

/**
 * <p>
 * 抽象工厂接口类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:45:32
 */
public interface AbstractFactory {

    AbstractProductA createProductA();

    AbstractProductB createProductB();

}
