package com.zukxu.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zukxu.jackson.controller.bean.TestEntity;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * api demo
 * </p>
 *
 * @author xupu
 * @since 2022/5/25 14:20:36
 */
public class JacksonApiDemo {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        //开启Jackson对LocalDateTime的支持
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws IOException {
        serializerObject();
        deserializerObject();
    }

    private static void deserializerObject() throws IOException {
        String jsonStr = "{\"id\":12345,\"text\":\"Jackson测试json序列化和反序列化\",\"createTime\":\"2022-05-26T14:40:56.85\"}";
        TestEntity testEntity = mapper.readValue(jsonStr, TestEntity.class);
        TestEntity testEntityFile = mapper.readValue(new File("testEntity.json"), TestEntity.class);
        byte[] array = mapper.writeValueAsBytes(testEntity);
        TestEntity testEntityArray = mapper.readValue(array, TestEntity.class);
    }

    private static void serializerObject() throws IOException {
        //单个对象序列化
        TestEntity entity = new TestEntity().setId(12345L).setText("Jackson测试json序列化和反序列化").setCreateTime(LocalDateTime.now());

        //对象转字符串：
        String jsonStr = mapper.writeValueAsString(entity);
        System.out.println(jsonStr);
        //对象转文件
        mapper.writeValue(new File("testEntity.json"), entity);
        //对象转数组
        byte[] array = mapper.writeValueAsBytes(entity);
        System.out.println(array);
    }

}
