package com.zukxu.order.config;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author xupu
 * @Description 重写FastJsonRedisSerializer
 * @Date 2021-09-23 14:35
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must be not null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<T> clazz) {
        return TypeFactory.defaultInstance().constructArrayType(clazz);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
