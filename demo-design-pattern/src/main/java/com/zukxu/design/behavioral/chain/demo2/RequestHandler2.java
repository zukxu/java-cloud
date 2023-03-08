package com.zukxu.design.behavioral.chain.demo2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 在这个接口中，我们定义了一个handleRequest方法，用于处理HTTP请求，同时这个方法的返回值为boolean类型，表示当前请求是否被处理。
 * 我们还定义了一个setNextHandler方法，用于设置下一个请求处理器
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:02:51
 */
public interface RequestHandler2 {

    boolean handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;

    void setNextHandler(RequestHandler2 handler);

}
