package com.zukxu.mybatis.dynamic;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义的动态切换数据源的类 需要继承AbstractRoutingDataSource 类
 * 并且重写其中的 determineCurrentLookupKey() 方法
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:40:37
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 返回需要使用的数据源的key，将会按照这个KEY从Map获取对应的数据源（切换）
     *
     * @return Object
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从ThreadLocal中取出KEY
        return DataSourceType.getDataBaseType();
    }

}
