package com.zukxu.mybatis.annotations;

import com.zukxu.mybatis.handler.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p>
 * 注解切面类
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:44:53
 */
@Aspect
//优先级要设置在事务切面执行之前
@Order(1)
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(DynamicDataSource)")
    public void pointcut() {
    }

    /**
     * 在方法执行之前切换到指定的数据源
     *
     * @param joinPoint pointcut
     */
    @Before(value = "pointcut()")
    public void beforeOpt(JoinPoint joinPoint) {
        /*因为是对注解进行切面，所以这边无需做过多判定，直接获取注解的值，进行环绕，将数据源设置成远方，然后结束后，清楚当前线程数据源*/
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DynamicDataSource dataSource = method.getAnnotation(DynamicDataSource.class);
        log.info("[Switch DataSource]:" + dataSource.value());
        DataSourceHolder.setDataSource(dataSource.value());
    }

    /**
     * 方法执行之后清除掉ThreadLocal中存储的KEY，这样动态数据源会使用默认的数据源
     */
    @After(value = "pointcut()")
    public void afterOpt() {
        DataSourceHolder.clearDataSource();
        log.info("[Switch Default DataSource]");
    }

}
