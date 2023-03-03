package com.zukxu.design.creation.factory;

import com.zukxu.design.creation.factory.service.Factory;
import com.zukxu.design.creation.factory.service.Product;

/**
 * <p>
 * 工厂B，实现工厂接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 15:40:38
 */
public class FactoryB implements Factory {

    @Override
    public Product createProduct() {
        return new ProductB();
    }

}