package com.zukxu.democache.template;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * 三级缓存模板类
 *
 * @author xupu
 * @since 2026/1/4 11:32:51
 */
@Component
@Slf4j
public class CacheTemplate<K, V> {
    private final Cache<K, V> local = Caffeine.newBuilder()
                                              .maximumSize(10_000)
                                              .expireAfterWrite(Duration.ofSeconds(60))
                                              .recordStats()//命中率监控
                                              .build();

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    /**
     * 金字塔查询
     */
    public V get(K key, Supplier<V> dbFallback) {
        // L1 本地查询
        V v = local.getIfPresent(key);
        if (v != null) {
            log.info("L1 hit {}", key);
            return v;
        }
        // L2 Redis
        v = redisTemplate.opsForValue().get(key);
        if (v != null) {
            local.put(key, v);//回填L1
            log.info("L2 hit {}", key);
            return v;
        }
        // L3 DB
        v = dbFallback.get();
        if (v != null) {
            set(key, v);
        }
        return v;
    }

    /**
     * 双写 L1 和 L2
     *
     * @param key   k
     * @param value v
     */
    public void set(K key, V value) {
        local.put(key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 双删 L1 和 L2
     *
     * @param key k
     */
    public void evict(K key) {
        local.invalidate(key);
        redisTemplate.delete(key);
    }

    /**
     * 打印RT结果
     */
    @Scheduled(fixedDelay = 30_000)
    public void printStats() {
        log.info("L1 hitRate {}", local.stats().hitRate());
    }
}
