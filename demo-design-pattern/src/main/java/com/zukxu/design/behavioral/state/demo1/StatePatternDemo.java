package com.zukxu.design.behavioral.state.demo1;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:13:53
 */
public class StatePatternDemo {


    public static void main(String[] args) {
        Context context = new Context();

        context.request(); // 输出：当前状态是 A.

        context.setState(new ConcreteStateB());
        context.request(); // 输出：当前状态是 B.

    }

}
