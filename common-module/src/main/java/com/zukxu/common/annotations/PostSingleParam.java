package com.zukxu.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Post请求传递单个参数
 * </p>
 *
 * @author xupu
 * @since 2022-04-02 11:01
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostSingleParam {
    /**
     * 参数
     *
     * @return
     */
    String value();

    /**
     * 是否必填
     *
     * @return
     */
    boolean required() default true;
}
