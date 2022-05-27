package com.zukxu.jackson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/5/24 17:08:26
 */
@Data
@Accessors(chain = true)
@JsonRootName("test")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class TestEntity {

    /**
     * 推特消息id
     */
    Long id;

    /**
     * 消息内容
     */
    @JsonProperty(value = "TEXT", index = 1)
    String text;

    /**
     * 创建时间
     */
    LocalDateTime createTime;

}
