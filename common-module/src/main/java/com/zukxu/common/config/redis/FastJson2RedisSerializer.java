package com.zukxu.common.config.redis;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @author xupu
 * @Description 重写FastJson2RedisSerializer
 * @Date 2021-09-23 14:35
 */
public class FastJson2RedisSerializer<T> implements RedisSerializer<T> {

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> clazz;

    public FastJson2RedisSerializer(Class<T> clazz) {
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
        if(t == null) return new byte[0];
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length <= 0) return null;
        String s = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(s, clazz);
    }

}
