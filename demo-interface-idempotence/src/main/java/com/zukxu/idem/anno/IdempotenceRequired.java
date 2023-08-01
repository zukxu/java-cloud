package com.zukxu.idem.anno;

import java.lang.annotation.*;

/**
 * <p>
 * 幂等性 注解
 * </p>
 *
 * @author xupu
 * @since 2022/12/14 20:40:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotenceRequired {

}