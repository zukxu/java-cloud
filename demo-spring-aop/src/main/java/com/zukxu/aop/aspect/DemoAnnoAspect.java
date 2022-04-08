package com.zukxu.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
public class DemoAnnoAspect {

    @Pointcut("@annotation(com.zukxu.aop.aspect.Perms)")
    private void permCheck() {}

    @Around("permCheck()")
    public Object permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===================第一个切面===================：{}", System.currentTimeMillis());

        //获取请求参数，详见接口类
        Object[] objects = joinPoint.getArgs();
        Long id = ((JSONObject) objects[0]).getLong("id");
        String name = ((JSONObject) objects[0]).getString("name");
        log.info("id1->>>>>>>>>>>>>>>>>>>>>>{}", id);
        log.info("name1->>>>>>>>>>>>>>>>>>>>>>{}", name);

        // id小于0则抛出非法id的异常
        if (id < 0) {
            return JSON.parseObject("{\"message\":\"illegal id\",\"code\":403}");
        }
        return joinPoint.proceed();
    }
}
