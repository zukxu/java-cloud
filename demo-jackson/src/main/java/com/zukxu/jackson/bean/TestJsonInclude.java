package com.zukxu.jackson.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 测试 JsonInclude 注解的详情
 * </p>
 *
 * @author xupu
 * @since 2022/5/26 15:57:56
 */
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
//@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = CustomFilter.class)
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonInclude(JsonInclude.Include.NON_ABSENT)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
@Accessors(chain = true)
public class TestJsonInclude {

    private Long id;

    private String name;

    private Integer age;

    private Boolean isAdmin = true;

    private Optional<String> optional;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AtomicReference<String> atomic;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Map<String, Object>> hobby;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AtomicReference<String> getAtomic() {
        return atomic;
    }

}
