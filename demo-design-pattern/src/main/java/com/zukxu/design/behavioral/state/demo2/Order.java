package com.zukxu.design.behavioral.state.demo2;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:17:12
 */
@Data
public class Order {

    private User user;

    private State currentState;

    public Order(User user) {
        this.user = user;
        this.currentState = new PendingState();
    }

    public State getState() {
        return this.currentState;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void handle() {
        this.currentState.handle(this);
    }

    public Long getUserId() {
        return user.getId();
    }

    // Getters and setters
}
