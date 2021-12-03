package com.zukxu.model;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author xupu
 * @since 2021-12-03 14:36
 */
@Data
public class Order {
    private Long id;
    private Long num;
    private String orderName;
    private List<Product> productList;

    public Order() {
    }

    public Order(Long id, Long num, String orderName, List<Product> productList) {
        this.id = id;
        this.num = num;
        this.orderName = orderName;
        this.productList = productList;
    }
}
