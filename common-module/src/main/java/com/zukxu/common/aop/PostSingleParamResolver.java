package com.zukxu.common.aop;

import com.zukxu.common.annotations.PostSingleParam;
import com.zukxu.common.utils.ServletUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 注解对应的AOP解析器
 * </p>
 *
 * @author xupu
 * @since 2022-04-02 11:17
 */
public class PostSingleParamResolver implements HandlerMethodArgumentResolver {
    /**
     * 判断是否包含某个元素
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PostSingleParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //通过方法获取参数中的注解
        PostSingleParam postSingleParam = parameter.getParameterAnnotation(PostSingleParam.class);
        //通过request获取传递的参数
        return ServletUtils.getParameter(webRequest.getNativeRequest(HttpServletRequest.class), postSingleParam.value());

    }
}
