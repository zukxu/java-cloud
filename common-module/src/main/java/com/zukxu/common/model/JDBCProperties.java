package com.zukxu.common.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * JDBC
 * </p>
 *
 * @author xupu
 * @since 2022/8/15 14:53:10
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class JDBCProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

}
