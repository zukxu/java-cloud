package com.zukxu.shutdown.config;

import com.zukxu.shutdown.bean.TerminateBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/16 0016 14:47
 */
@Configuration
public class ShutDownConfig {
    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
