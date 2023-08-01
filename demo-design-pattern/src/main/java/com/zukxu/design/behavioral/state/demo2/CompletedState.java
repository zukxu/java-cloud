package com.zukxu.design.behavioral.state.demo2;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:16:11
 */
public class CompletedState implements State {

    public void handle(Order order) {
        if (order.getUser().hasPermission("complete")) {
            System.out.println("订单已完成，订单状态为已完成。");
        } else {
            throw new RuntimeException("当前用户没有完成订单的权限。");
        }
    }

}
