package com.zukxu.design.strategy.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/6/9 16:56:50
 */
@Service
public class SimpleContext {

    @Autowired
    private final Map<String, ResourceStrategy> strategyMap = new ConcurrentHashMap<>();

    public SimpleContext(Map<String, ResourceStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(strategyMap::put);
    }

    public String getResource(String poolId) {
        return strategyMap.get(poolId).orderInformation(poolId);
    }

}