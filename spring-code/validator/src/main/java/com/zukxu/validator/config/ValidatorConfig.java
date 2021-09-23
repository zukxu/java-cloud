package com.zukxu.validator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <p>
 * 配置类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:14
 */
@Configuration
public class ValidatorConfig {
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();

		return validatorFactory.getValidator();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		//默认是普通模式，会返回所有的验证不通过信息集合
		MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
		processor.setValidator(validator());
		return processor;
	}
}
