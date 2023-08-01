package com.zukxu.design.behavioral.chain.demo2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 我们定义一个抽象类AbstractRequestHandler，用于实现RequestHandler接口中的setNextHandler方法
 * </p>
 * 我们实现了RequestHandler接口中的setNextHandler方法，并且定义了一个protected类型的handleNext方法，用于处理下一个请求处理器
 *
 * @author xupu
 * @since 2023/3/8 16:03:15
 */
public abstract class AbstractRequestHandler implements RequestHandler2 {

    private RequestHandler2 nextHandler;

    @Override
    public void setNextHandler(RequestHandler2 handler) {
        this.nextHandler = handler;
    }

    protected boolean handleNext(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (nextHandler != null) {
            return nextHandler.handleRequest(request, response);
        } else {
            return true;
        }
    }

}
