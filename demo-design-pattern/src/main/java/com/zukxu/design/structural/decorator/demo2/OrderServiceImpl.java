package com.zukxu.design.structural.decorator.demo2;

import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/10 15:47:11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void processOrder(Order order) {
        // 处理订单并将其保存到数据库中
        System.out.println("处理订单并将其保存到数据库中");
        // ...
    }

}

