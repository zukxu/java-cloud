package com.zukxu.design.creation.factory;

import com.zukxu.design.creation.factory.service.Product;

/**
 * <p>
 * 产品B，实现产品接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:39:04
 */
public class ProductB implements Product {

    @Override
    public void doSomething() {
        System.out.println("Product B is doing something...");
    }

}