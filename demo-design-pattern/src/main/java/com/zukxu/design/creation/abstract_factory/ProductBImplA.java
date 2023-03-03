package com.zukxu.design.creation.abstract_factory;

import com.zukxu.design.creation.abstract_factory.service.AbstractProductB;

/**
 * <p>
 * 产品B实现B接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:50:19
 */
public class ProductBImplA implements AbstractProductB {

    @Override
    public void operationB() {
        System.out.println("Product B Implementation A operation B.");
    }

}