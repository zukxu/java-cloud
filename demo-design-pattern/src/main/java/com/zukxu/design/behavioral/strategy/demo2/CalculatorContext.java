package com.zukxu.design.behavioral.strategy.demo2;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 该类可以接受一个策略对象，并将算法执行委托给该策略对象
 * 使用Spring的@Service注解将CalculatorContext类声明为Spring Bean，以便在应用程序中使用它。
 * 我们还使用一个Map来存储所有的策略对象，并在executeStrategy()方法中根据运算符选择相应的策略对象
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 14:38:07
 */
@Service
public class CalculatorContext {

    private final Map<String, CalculatorStrategy> strategies;

    public CalculatorContext(Map<String, CalculatorStrategy> strategies) {
        this.strategies = strategies;
    }

    public int executeStrategy(String operator, int num1, int num2) {
        CalculatorStrategy strategy = strategies.get(operator);
        if(strategy == null) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        return strategy.calculate(num1, num2);
    }

}
