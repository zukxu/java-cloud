package com.zukxu.mybatis.dynamic.annotations;

import java.lang.annotation.*;

/**
 * <p>
 * 切换数据源注解 可以用于类或者方法级别
 * 优先级：方法级别 > 类级别
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:42:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
public @interface DataSource {

    /**
     * 默认切换的数据源KEY
     */
    String DEFAULT_NAME = "primary";

    /**
     * 需要切换到数据的KEY
     */
    String value() default DEFAULT_NAME;

}
