package com.zukxu.design.structural.decorator.demo2;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/10 15:44:55
 */
public class Order {

    private int orderId;

    private String customerName;

    private List<String> items;

    public Order(int orderId, String customerName, List<String> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customerName=" + customerName + ", items=" + items + "]";
    }

}
