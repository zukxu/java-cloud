package com.zukxu.mybatis.config;

import com.zukxu.mybatis.dynamic.DataSourceType;
import com.zukxu.mybatis.dynamic.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 动态数据源配置类
 * </p>
 *
 * @author xupu
 * @since 2022/7/5 17:34:11
 */
@Configuration
@MapperScan(basePackages = "com.zukxu.mybatis.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DynamicDataSourceConfig {

    // 将这个对象放入Spring容器中
    @Bean(name = "PrimaryDataSource")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource getDataSourcePrimary() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource getDataSourceSecondary() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("PrimaryDataSource") DataSource primaryDataSource, @Qualifier("SecondaryDataSource") DataSource secondaryDataSource) {
        //这个地方是比较核心的, targetDataSource集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DataBaseType.Primary, primaryDataSource);
        targetDataSource.put(DataSourceType.DataBaseType.Secondary, secondaryDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(primaryDataSource);//设置默认对象
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean.getObject();
    }

}
