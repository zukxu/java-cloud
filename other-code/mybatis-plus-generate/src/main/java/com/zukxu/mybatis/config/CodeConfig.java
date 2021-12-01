package com.zukxu.mybatis.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * @author xupu
 * @Date 2021-12-01 14:43
 */
public class CodeConfig {

    /**
     * 数据源配置
     */
    public static final DataSourceConfig dataSourceConfig = new DataSourceConfig
            .Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus" , "root" ,"123456")
            .dbQuery(new MySqlQuery())//数据库查询方式
            .schema("mybatis-plus")//数据库名,某些数据库可能会用到
            .typeConvert(new MySqlTypeConvert())//数据库类型转换器 可自定义
            .keyWordsHandler(new MySqlKeyWordsHandler())//数据库关键字处理器 可自定义
            .build();

}
