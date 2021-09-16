package com.zukxu.common.result;

import lombok.Getter;

/**
 * @author xupu
 * @Description 返回结果集实体
 * @Date 2021-09-16 16:34
 */
@Getter
public enum RStatus {
    OK(200, "请求成功"),
    FAIL(500, "请求失败");

    private int code;
    private String message;

    RStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
