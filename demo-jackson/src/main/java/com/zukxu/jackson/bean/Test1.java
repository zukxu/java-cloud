package com.zukxu.jackson.bean;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zukxu.jackson.filter.LocalDateTimeToString;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/5/27 22:04:44
 */
public class Test1 {

    private String field0;

    @JacksonInject(value = "defaultField1")
    private String field1;

    @JacksonInject
    private String field2;

    @JsonSerialize(using = LocalDateTimeToString.class)
    private LocalDateTime field3;

}
