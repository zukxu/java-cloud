package com.zukxu.design.behavioral.state.demo2;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:17:32
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderRepository orderRepository;

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        User user = userRepository.findById(order.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("用户不存在。");
        }

        order.setUser(user);
        order.handle(); // 创建订单
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody Order order) {
        User user = userRepository.findById(order.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("用户不存在。");
        }

        Order existingOrder = getOrderById(id);
        if (existingOrder == null) {
            throw new RuntimeException("订单不存在。");
        }

        existingOrder.setUser(user);

        if (order.getState() instanceof State) {
            existingOrder.setState(new CompletedState());
        }

        orderRepository.save(existingOrder);
    }

    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return null;
        }
        return optionalOrder.get();
    }

}
