package com.zukxu.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 所有的get请求被调用前在控制台输出一句"get请求的advice触发了"
 * </p>
 *
 * @author xupu
 * @since 2022-04-08 15:08
 */
@Slf4j
@Aspect
@Component
public class DemoLogAspect {
    // 定义一个切点：所有被GetMapping注解修饰的方法会织入advice
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    // 定义一个切点：所有满足此表达式的类都会织入advice
    @Pointcut("execution(* com.zukxu.aop.controller..*.*(..))")
    private void DemoLogAspectPoint(){}

    @Before("DemoLogAspectPoint()")
    private void logAdvice(){
        log.info("get请求的advice触发了");
    }
}
