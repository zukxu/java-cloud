package com.zukxu.design.behavioral.chain.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 在这个配置类中，我们注入了三个具体的请求处理器：
 * RequestHandlerA、RequestHandlerB和RequestHandlerC。
 * 在addInterceptors方法中，我们创建了一个HandlerInterceptor对象，
 * 并在preHandle方法中按照责任链的顺序调用了这些请求处理器的handleRequest方法，以处理HTTP请求。
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:09:23
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestHandlerA requestHandlerA;

    @Autowired
    private RequestHandlerB requestHandlerB;

    @Autowired
    private RequestHandlerC requestHandlerC;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                /*如果handleRequest方法返回false，表示当前请求未被处理，则preHandle方法返回false，表示请求处理失败。
                如果handleRequest方法返回true，则preHandle方法继续调用下一个请求处理器的handleRequest方法，
                直到所有请求处理器都处理完毕，preHandle方法才返回true，表示请求处理成功*/
                boolean handled = requestHandlerA.handleRequest(request, response);
                if (!handled) {
                    return false;
                }

                handled = requestHandlerB.handleRequest(request, response);
                if (!handled) {
                    return false;
                }

                handled = requestHandlerC.handleRequest(request, response);
                if (!handled) {
                    return false;
                }

                return true;
            }
        };

        registry.addInterceptor(handlerInterceptor);
    }

}
