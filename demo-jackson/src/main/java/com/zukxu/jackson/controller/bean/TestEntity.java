package com.zukxu.jackson.controller.bean;

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
public class TestEntity {

    /**
     * 推特消息id
     */
    Long id;

    /**
     * 消息内容
     */
    String text;

    /**
     * 创建时间
     */
    LocalDateTime createTime;

}
