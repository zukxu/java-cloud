package com.yhq.sensitive.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 银行卡号脱敏
 *
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.yhq.sensitive.strategy.SensitiveChineseName.class, pattern = "(?<=\\w{4})\\w(?=\\w{4})", replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveBankCard {

}
