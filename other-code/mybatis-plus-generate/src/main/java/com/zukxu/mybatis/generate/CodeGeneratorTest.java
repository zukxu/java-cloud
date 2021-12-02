package com.zukxu.mybatis.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            "123456" );

    public static void main(String[] args) {
        testFastGenerate();
    }

    private static void testFastGenerate() {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("sys_user"))
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }

    @SneakyThrows
    private static void fastGenerate() {
        //4获取当前工程路径
        String path = Objects.requireNonNull(CodeGeneratorTest.class.getResource("" )).getPath();
        String basePath = path.substring(1, path.indexOf("target" )) + "src";
        System.out.println(basePath);
        String basePackage = "com.zukxu.mybatis.demo";
        String xmlPath = basePackage + File.separator + basePackage.replace("\\." , File.separator);
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("zukxu" ) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy-MM-dd HH:mm:ss" )
                            .outputDir(basePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(basePackage) // 设置父包名
                            .moduleName("system" ) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, xmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user" ) // 设置需要生成的表名
                            .addTablePrefix("sys_" ); // 设置过滤表前缀
                })
                .templateEngine(new VelocityTemplateEngine()).templateConfig(builder -> {
                    builder.entity("/vm/java/entity.java.vm" );
                    builder.controller("/vm/java/controller.java.vm" );
                    builder.service("/vm/java/service.java.vm" );
                    builder.serviceImpl("/vm/java/serviceImpl.java.vm" );
                    builder.mapper("/vm/java/mapper.java.vm" );
                    builder.mapperXml("/vm/xml/mapper.xml.vm" );
                })
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