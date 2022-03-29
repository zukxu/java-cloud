package com.zukxu.securityjwt.comm;

/**
 * <p>
 * security 常量池
 * </p>
 *
 * @author xupu
 * @since 2022/3/29 20:03
 */
public interface SecurityConstant {
    long EXPIRATION_TIME = 604800_000;     // 5天(以毫秒ms计)
    String SECRET = "CodeSheepSecret";      // JWT密码
    String TOKEN_PREFIX = "Bearer ";        // Token前缀
    String HEADER_STR = "Authorization"; // 存放Token的Header Key
}
