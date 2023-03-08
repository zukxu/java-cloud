package com.zukxu.design.behavioral.state.demo2;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:31:18
 */
@Repository
public interface OrderRepository {

    Optional<Order> findById(Long id);

    void save(Order existingOrder);

}