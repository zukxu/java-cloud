package com.zukxu.design.behavioral.chain.demo2;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 具体实现B
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:04:32
 */
@Component
public class RequestHandlerB extends AbstractRequestHandler {

    @Override
    public boolean handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int num = RandomUtil.getRandom().nextInt();
        if (num % 3 == 1) {
            return true;
        } else {
            // 请求未被处理，调用下一个请求处理器
            return handleNext(request, response);
        }
    }

}

