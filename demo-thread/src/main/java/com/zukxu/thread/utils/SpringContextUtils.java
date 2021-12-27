package com.zukxu.thread.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/1/22 0022 17:07
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static <T> T getBean(String name, Class<T> tClass) {
		return applicationContext.getBean(name, tClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}
}