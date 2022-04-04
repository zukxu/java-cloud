package com.zukxu.common.config.aop;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 注解对应的AOP解析器
 * </p>
 *
 * @author xupu
 * @since 2022-04-02 11:17
 */
@Slf4j
public class PostSingleParamResolver implements HandlerMethodArgumentResolver {


    private static final String POST = "post";

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PostSingleParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String contentType = Objects.requireNonNull(servletRequest).getContentType();

        if(StrUtil.isEmpty(contentType) || !StrUtil.contains(contentType, APPLICATION_JSON)) {
            log.error("PostSingleParam contentType需为【{}】", APPLICATION_JSON);
            throw new RuntimeException("PostSingleParam contentType需为application/json");
        }

        if(!StrUtil.equalsIgnoreCase(POST, servletRequest.getMethod())) {
            log.error("PostSingleParam 请求类型必须为post");
            throw new RuntimeException("PostSingleParam 请求类型必须为post");
        }
        return this.bindRequestParams(parameter, servletRequest);
    }

    private Object bindRequestParams(MethodParameter parameter, HttpServletRequest servletRequest) {
        PostSingleParam PostSingleParam = parameter.getParameterAnnotation(PostSingleParam.class);
        Class<?> parameterType = parameter.getParameterType();
        String requestBody;
        try {
            requestBody = ServletUtils.getRequestBody(servletRequest);
        } catch(IOException e) {
            log.error("PostSingleParam 读取流异常", e);
            throw new RuntimeException("PostSingleParam 读取流异常");
        }
        Map paramObj = JSONObject.parseObject(requestBody, Map.class);
        if(ObjectUtil.isNull(paramObj)) {
            paramObj = new JSONObject();
        }

        String parameterName = StringUtils.isBlank(PostSingleParam.value())
                ? parameter.getParameterName()
                : PostSingleParam.value();
        Object value = paramObj.get(parameterName);

        if(PostSingleParam.required()) {
            if(ObjectUtil.isNull(value)) {
                log.error("PostSingleParam require=true,参数【{}】不能为空!", parameterName);
                throw new RuntimeException("《PostSingleParam》 " + parameterName + "不能为空!");
            }
        }

        return Convert.convert(parameterType, value);
    }

}
