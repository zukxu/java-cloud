package com.zukxu.design.behavioral.chain.demo1;

/**
 * <p>
 * 处理请求的接口
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:54:29
 */
public interface RequestHandler {

    void handleRequest(Request request);

    RequestHandler getNextHandler();

    void setNextHandler(RequestHandler handler);

}
