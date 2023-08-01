package com.zukxu.design.behavioral.state.demo2;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:16:11
 */
public class PaidState implements State {

    public void handle(Order order) {
        if (order.getUser().hasPermission("pay")) {
            System.out.println("订单支付成功，订单状态为已支付。");
        } else {
            throw new RuntimeException("当前用户没有支付订单的权限。");
        }
    }

}
