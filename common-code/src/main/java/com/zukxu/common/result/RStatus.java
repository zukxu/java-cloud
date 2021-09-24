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
    FAIL(500, "请求失败"),
    PARAM_ERROR(501, "参数错误"),
    //账号相关
    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    DUPLICATE_ACCOUNT(12, "账号重复"),
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    INCORRECT_CREDENTIALS(14, "账号或密码错误"),
    NOT_LOGIN_IN(15, "账号未登录"),
    UNAUTHORIZED(16, "账号没有权限");
    private int code;
    private String message;

    RStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
