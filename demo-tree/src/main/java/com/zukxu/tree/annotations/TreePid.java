package com.zukxu.tree.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 标识Pid
 *
 * @author zukxu
 * @since 2022/1/2 19:13:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface TreePid {}
