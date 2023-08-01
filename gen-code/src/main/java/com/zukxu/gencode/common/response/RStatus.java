package com.zukxu.gencode.common.response;

import lombok.Getter;

/**
 * @author xupu
 * @Description R返回类状态
 * @Date 2021-09-15 9:49
 */
@Getter
public enum RStatus {
    OK(200, "成功"), FAIL(500, "失败"), UNKNOWN_ERROR(5001, "未知错误"), PARAM_ERROR(5002, "参数错误"), SYSTEM_INNER_ERROR(5003, "系统内部错误"),
    ;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String msg;

    RStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}