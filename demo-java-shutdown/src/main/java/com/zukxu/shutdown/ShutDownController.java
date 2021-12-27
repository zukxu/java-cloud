package com.zukxu.shutdown;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/16 0016 15:10
 */
@RestController
public class ShutDownController implements ApplicationContextAware {
	private ApplicationContext context;

	@PostMapping("/shutDownContext")
	public String shutDownContext() {
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
		ctx.close();
		return "context is shutdown";
	}

	@GetMapping("/")
	public String getIndex() {
		return "OK";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
