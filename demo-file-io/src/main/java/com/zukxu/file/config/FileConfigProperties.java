package com.zukxu.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-18 16:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfigProperties {
    private String uploadPath;
    private String returnPath;
}