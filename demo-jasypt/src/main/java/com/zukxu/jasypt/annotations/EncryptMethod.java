package com.zukxu.jasypt.annotations;

import java.lang.annotation.*;

import static com.zukxu.jasypt.constant.EncryptConstant.ENCRYPT;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-22 15:32
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptMethod {
    String type() default ENCRYPT;
}