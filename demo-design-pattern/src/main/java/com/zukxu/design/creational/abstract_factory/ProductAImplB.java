package com.zukxu.design.creational.abstract_factory;

import com.zukxu.design.creational.abstract_factory.service.AbstractProductA;

/**
 * <p>
 * 产品B实现B接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:50:19
 */
public class ProductAImplB implements AbstractProductA {

    @Override
    public void operationA() {
        System.out.println("Product A Implementation B operation A.");
    }

}