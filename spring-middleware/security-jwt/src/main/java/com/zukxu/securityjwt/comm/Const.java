package com.zukxu.securityjwt.comm;

/**
 * @author xupu
 * @Description
 * @Date 2021-10-13 16:16
 */
public interface Const {
    long EXPIRATION_TIME = 432_000_000;     // 5天(以毫秒ms计)
    String SECRET = "CodeSheepSecret";      // JWT密码
    String TOKEN_PREFIX = "Bearer ";        // Token前缀
    String HEADER_STRING = "Authorization"; // 存放Token的Header Key
}
