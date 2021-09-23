package com.zukxu.thread.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/22 0022 17:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {
	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
	private int keepAliveSeconds;
}