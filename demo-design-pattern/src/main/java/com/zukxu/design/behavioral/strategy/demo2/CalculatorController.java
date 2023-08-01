package com.zukxu.design.behavioral.strategy.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 14:39:55
 */
@RestController
public class CalculatorController {

    @Autowired
    private CalculatorContext calculatorContext;

    @GetMapping("/calculate/{operator}/{num1}/{num2}")
    public int calculate(@PathVariable String operator, @PathVariable int num1, @PathVariable int num2) {
        return calculatorContext.executeStrategy(operator, num1, num2);
    }

}
