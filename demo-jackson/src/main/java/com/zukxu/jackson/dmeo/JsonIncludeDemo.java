package com.zukxu.jackson.dmeo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.zukxu.jackson.bean.TestJsonInclude;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/5/26 16:05:05
 */
public class JsonIncludeDemo {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void main(String[] args) throws JsonProcessingException {
        Class<TestJsonInclude> clazz = TestJsonInclude.class;
        if (clazz.isAnnotationPresent(JsonInclude.class)) {
            // 获取 "类" 上的注解
            JsonInclude getAnnotation = clazz.getAnnotation(JsonInclude.class);
            testInclude(String.valueOf(getAnnotation.value()));
            // ALWAYS 默认策略，任何情况都执行序列化
            // NON_NULL 非空
            // NON_ABSENT null的不会序列化，但如果类型是AtomicReference，依然会被序列化
            // NON_EMPTY null、集合数组等没有内容、空字符串等，都不会被序列化
            // NOM_DEFAULT 如果字段是默认值，就不会被序列化
            // CUSTOM 此时要指定valueFilter属性，该属性对应一个类，用来自定义判断被JsonInclude修饰的字段是否序列化
            // USE_DEFAULTS 当JsonInclude在类和属性上都有时，优先使用属性上的注解，此时如果在序列化的get方法上使用了JsonInclude，并设置为USE_DEFAULTS，就会使用类注解的设置
        }
    }

    private static void testInclude(String type) throws JsonProcessingException {
        System.out.println("//@JsonInclude(JsonInclude.Include." + type);
        if ("NonAbsent".equals(type)) {
            mapper.registerModule(new Jdk8Module());
        }
        TestJsonInclude test = new TestJsonInclude().setId(1L)
                                                    .setName("")
                                                    .setAge(null)
                                                    .setOptional(Optional.empty())
                                                    .setAtomic(new AtomicReference<>())
                                                    .setHobby(new ArrayList<>());
        System.out.println(mapper.writeValueAsString(test));
    }
}
