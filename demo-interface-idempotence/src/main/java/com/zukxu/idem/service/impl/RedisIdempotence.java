package com.zukxu.idem.service.impl;

import com.zukxu.idem.service.Idempotence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * redis 实现幂等性
 * </p>
 *
 * @author xupu
 * @since 2022/12/14 20:39:20
 */
@Service
public class RedisIdempotence implements Idempotence {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean check(String idempotenceId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(idempotenceId));
    }

    @Override
    public void record(String idempotenceId) {
        redisTemplate.opsForValue().set(idempotenceId, "1");
    }

    @Override
    public void record(String idempotenceId, Integer time) {
        redisTemplate.opsForValue().set(idempotenceId, "1", time);
    }

    @Override
    public void delete(String idempotenceId) {
        redisTemplate.delete(idempotenceId);
    }

}