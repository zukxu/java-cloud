package com.zukxu.design.behavioral.state.demo2;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:16:11
 */
public class PendingState implements State {

    public void handle(Order order) {
        if(order.getUser().hasPermission("create")) {
            System.out.println("创建订单成功，订单状态为待支付。");
        } else {
            throw new RuntimeException("当前用户没有创建订单的权限。");
        }
    }

}
