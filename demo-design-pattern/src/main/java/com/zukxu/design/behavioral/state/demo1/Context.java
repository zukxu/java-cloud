package com.zukxu.design.behavioral.state.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:13:29
 */
public class Context {

    private State currentState;

    public Context() {
        this.currentState = new ConcreteStateA();
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void request() {
        this.currentState.handle();
    }

}
