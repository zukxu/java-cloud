package com.zukxu.design.behavioral.strategy.demo2;

import org.springframework.stereotype.Component;

/**
 * <p>
 * 具体实现类 加法
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 11:51:11
 */
@Component
public class AdditionStrategy implements CalculatorStrategy {

    @Override
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }

}

