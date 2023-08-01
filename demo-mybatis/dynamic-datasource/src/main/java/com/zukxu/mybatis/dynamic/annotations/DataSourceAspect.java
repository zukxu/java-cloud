package com.zukxu.mybatis.dynamic.annotations;

import com.zukxu.mybatis.dynamic.DataSourceType;
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
 * 动态数据源切面类
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:44:53
 */
//优先级要设置在事务切面执行之前
@Order(1)
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.zukxu.mybatis.dynamic.annotations.DataSource)")
    public void pointcut() {
    }

    /**
     * 方法执行前拦截我们的注解
     */
    @Before(value = "pointcut()")
    public void changeDataSource(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        String value = dataSource.value();
        if (DataSourceType.DataBaseType.Primary.name().equalsIgnoreCase(value)) {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Primary);
        } else if (DataSourceType.DataBaseType.Secondary.name().equalsIgnoreCase(value)) {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Secondary);
        } else {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Primary);
        }
        log.info("[DataSource Switch]:{}", value);
    }

    /**
     * 清除数据源配置,这样动态数据源会使用默认的数据源
     */
    @After(value = "pointcut()")
    public void restoreDataSource() {
        DataSourceType.clearDataBaseType();
        log.info("[Switch Default DataSource]");
    }

}
