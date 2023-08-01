package com.zukxu.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-16 17:52
 */
@Configuration
public class ProjectConfig implements WebMvcConfigurer {
    @Resource
    private FileConfigProperties fileConfigProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileConfigProperties.getReturnPath() + "/**").addResourceLocations("file:/" + fileConfigProperties.getUploadPath() + "/");
    }
}
