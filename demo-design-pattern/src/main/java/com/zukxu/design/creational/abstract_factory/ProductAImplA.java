package com.zukxu.design.creational.abstract_factory;

import com.zukxu.design.creational.abstract_factory.service.AbstractProductA;

/**
 * <p>
 * 产品A实现A接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:50:19
 */
public class ProductAImplA implements AbstractProductA {

    @Override
    public void operationA() {
        System.out.println("Product A Implementation A operation A.");
    }

}