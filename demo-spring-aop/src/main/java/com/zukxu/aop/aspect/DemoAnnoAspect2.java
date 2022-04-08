package com.zukxu.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 拦截自定义注解的切面
 * </p>
 *
 * @author xupu
 * @since 2022-04-08 16:31
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class DemoAnnoAspect2 {

    @Pointcut("@annotation(com.zukxu.aop.aspect.Perms)")
    private void permCheck() {}

    @Around("permCheck()")
    public Object permissionCheckSecond(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===================第二个切面===================：{}", System.currentTimeMillis());
        return joinPoint.proceed();
    }
}
