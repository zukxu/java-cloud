package com.zukxu.design.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 享元工厂类
 * </p>
 *
 * @author xupu
 * @since 2023/3/3 17:52:22
 */
public class FlyweightFactory {

    private Map<Integer, Flyweight> flyweights = new HashMap<>();

    public Flyweight getFlyweight(int intrinsicState) {
        Flyweight flyweight = flyweights.get(intrinsicState);
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(intrinsicState);
            flyweights.put(intrinsicState, flyweight);
        }
        return flyweight;
    }

}
