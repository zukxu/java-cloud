package com.zukxu.design.behavioral.chain.demo2;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 具体实现A
 * 我们实现了handleRequest方法，用于处理HTTP请求。如果请求被处理，方法返回true；否则，方法调用handleNext方法，将请求交给下一个请求处理器进行处理。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:04:32
 */
@Component
public class RequestHandlerA extends AbstractRequestHandler {

    @Override
    public boolean handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 处理请求
        int num = RandomUtil.getRandom().nextInt();
        if(num % 3 == 1) {
            return true;
        } else {
            // 请求未被处理，调用下一个请求处理器
            return handleNext(request, response);
        }
    }

}

