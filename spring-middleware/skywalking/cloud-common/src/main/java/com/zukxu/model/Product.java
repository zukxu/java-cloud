package com.zukxu.model;

import lombok.Data;

/**
 * <p>
 * 产品
 * </p>
 *
 * @author xupu
 * @since 2021-12-03 14:36
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Long price;
    private Long count;

    public Product() {
    }

    public Product(Long id, String name, Long price, Long count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
