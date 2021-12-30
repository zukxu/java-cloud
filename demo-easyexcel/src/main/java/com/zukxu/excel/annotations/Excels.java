package com.zukxu.excel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Excel注解集
 * </p>
 *
 * @author xupu
 * @since 2021/12/13 14:21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {
    Excel[] value();
}
