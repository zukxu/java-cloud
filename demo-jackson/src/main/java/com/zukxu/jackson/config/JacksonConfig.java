package com.zukxu.jackson.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/5/26 15:30:14
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        //序列化设置
        mapper.enable(SerializationFeature.INDENT_OUTPUT);//格式化输出
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);//空指针不抛出异常
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);//关闭时间序列化为时间戳格式

        //反序列化设置
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//反序列化时遇到未知属性不抛出异常
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);//反序列化时空字符串为null

        //配置 序列化和反序列化都支持
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);//允许注释
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//允许字段名不包括引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//允许单引号

        //配合注解@JsonRootName()使用
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);//在json 序列化时包裹上对象名称 默认是实例的类名，如果实例有JsonRootName注解，就是该注解的value值
        return mapper;
    }

}
