package com.zukxu.design.structural.decorator.demo2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 假设我们正在开发一个在线商城应用程序，其中有一个订单服务。订单服务负责处理订单，并将其保存到数据库中。
 * 我们想要在订单服务中添加日志记录功能，以便更好地跟踪订单的状态。可以使用装饰器模式来实现此功能
 * </p>
 *
 * @author xupu
 * @since 2023/3/10 16:24:56
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        orderService.processOrder(order);
        return ResponseEntity.ok().build();
    }

}
