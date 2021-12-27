package com.zukxu.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-16 17:52
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {
    @Value("${file.returnPath}")
    private String returnPath;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(returnPath + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }
}
