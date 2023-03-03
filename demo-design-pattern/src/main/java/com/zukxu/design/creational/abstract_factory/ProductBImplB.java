package com.zukxu.design.creational.abstract_factory;

import com.zukxu.design.creational.abstract_factory.service.AbstractProductB;

/**
 * <p>
 * 产品B实现B接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:50:19
 */
public class ProductBImplB implements AbstractProductB {

    @Override
    public void operationB() {
        System.out.println("Product B Implementation B operation B.");
    }

}