package com.zukxu.common.config;

import com.zukxu.common.aop.PostSingleParamResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>
 * MVC config
 * </p>
 *
 * @author xupu
 * @since 2022-04-02 15:17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //配置添加注解解析器
        resolvers.add(new PostSingleParamResolver());
    }
}
