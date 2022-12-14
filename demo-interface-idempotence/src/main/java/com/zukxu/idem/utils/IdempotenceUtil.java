package com.zukxu.idem.utils;

import cn.hutool.core.lang.UUID;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 幂等性工具类
 * </p>
 *
 * @author xupu
 * @since 2022/12/14 20:40:03
 */
@Component
public class IdempotenceUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 生成幂等号
     */
    public String generateId() {
        String uuid = UUID.fastUUID().toString();
        String uId = Base64Util.encode(uuid).toLowerCase();
        redisTemplate.opsForValue().set(uId, "1", 1800);
        return uId;
    }

    /**
     * 从Header里面获取幂等号
     */
    public String getHeaderIdempotenceId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("idempotenceId");
    }

}