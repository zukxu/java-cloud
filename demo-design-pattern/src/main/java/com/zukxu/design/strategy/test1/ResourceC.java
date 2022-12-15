package com.zukxu.design.strategy.test1;

import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/6/9 16:54:22
 */
@Component("C")
public class ResourceC implements ResourceStrategy {

    @Override
    public String orderInformation(String id) {
        System.out.println("策略选择：Strategy C");
        return "C";
    }

}