package com.zukxu.design.structural.decorator.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/10 15:47:33
 */
@Slf4j
@Service
public class LoggingOrderServiceDecorator implements OrderService {

    private final OrderService delegate;

    public LoggingOrderServiceDecorator(@Qualifier("orderService") OrderService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void processOrder(Order order) {
        log.info("Processing order: {}", order);

        delegate.processOrder(order);

        log.info("Order processed: {}", order);
    }

}

