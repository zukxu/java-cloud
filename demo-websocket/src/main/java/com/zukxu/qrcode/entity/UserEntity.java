package com.zukxu.qrcode.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zukxu
 * CreateTime: 2021/7/13 0013 16:53
 */
@Data
@Accessors(chain = true)
public class UserEntity {
    private String uuid;
    private String userId;
    private LocalDateTime loginTime;
    private LocalDateTime createTime;
    private int state;
}
