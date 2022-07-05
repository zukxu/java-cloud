package com.zukxu.mybatis.annotations;

import java.lang.annotation.*;

/**
 * <p>
 * 切换数据源注解
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:42:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface DynamicDataSource {

    /**
     * 默认切换的数据源KEY
     */
    String DEFAULT_NAME = "master";

    /**
     * 需要切换到数据的KEY
     */
    String value() default DEFAULT_NAME;

}
