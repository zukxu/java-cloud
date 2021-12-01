package com.zukxu.mybatis.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 快速生成
 *
 * @author xupu
 * @Date 2021-12-01 11:39
 */
public class CodeGeneratorTest {

    /**
     * 数据源配置
     */
    public static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(
            "jdbc:mysql://127.0.0.1:3306/mybatis-plus" ,
            "root" ,
            "123456");

    public static void main(String[] args) {

    }

    private static void fastGenerate() {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                         .globalConfig(builder -> {
                             builder.author("baomidou") // 设置作者
                                    .enableSwagger() // 开启 swagger 模式
                                    .fileOverride() // 覆盖已生成文件
                                    .outputDir("D://"); // 指定输出目录
                         })
                         .packageConfig(builder -> {
                             builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                                    .moduleName("system") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                                                       "D://")); // 设置mapperXml生成路径
                         })
                         .strategyConfig(builder -> {
                             builder.addInclude("t_simple") // 设置需要生成的表名
                                    .addTablePrefix("t_" , "c_"); // 设置过滤表前缀
                         })
                         .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                         .execute();
    }

    private static void interactiveGenerate() {

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                         // 全局配置
                         .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
                         // 包配置
                         .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                         // 策略配置
                         .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply(
                                                                              "请输入表名，多个英文逗号分隔？所有输入 all")))
                                                                      .controllerBuilder()
                                                                      .enableRestStyle()
                                                                      .enableHyphenStyle()
                                                                      .entityBuilder()
                                                                      .enableLombok()
                                                                      .addTableFills(
                                                                              new Column("create_time" ,
                                                                                         FieldFill.INSERT))
                                                                      .build())
                         /*
                             模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                            .templateEngine(new BeetlTemplateEngine())
                            .templateEngine(new FreemarkerTemplateEngine())
                          */
                         .execute();


    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}