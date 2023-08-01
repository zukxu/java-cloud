package com.zukxu.gencode.generator.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

/**
 * 所有的配置项
 *
 * @author xupu
 * @Date 2021-12-01 14:43
 */
public class CodeConfigAll {

    /**
     * 数据源配置
     */
    public static final DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/mybatis-plus",
                                                                                         "root",
                                                                                         "123456").dbQuery(new MySqlQuery())//数据库查询方式
                                                                                                  .schema("mybatis-plus")//数据库名,某些数据库可能会用到
                                                                                                  .typeConvert(new MySqlTypeConvert())//数据库类型转换器 可自定义
                                                                                                  .keyWordsHandler(new MySqlKeyWordsHandler())//数据库关键字处理器 可自定义
                                                                                                  .build();

    /**
     * 全局配置
     */
    public static final GlobalConfig GLOBAL_CONFIG = new GlobalConfig.Builder().fileOverride()//覆盖已生成文件	默认值:false
                                                                               .disableOpenDir()//禁止打开输出目录	默认值:true
                                                                               .outputDir("D://temp")//指定输出目录	默认值: windows:D:// linux or mac : /tmp
                                                                               .author("baomidou")//作者名	baomidou 默认值:作者
                                                                               .enableKotlin()//开启 kotlin 模式	默认值:false
                                                                               .enableSwagger()//开启 swagger 模式	默认值:false
                                                                               .dateType(DateType.TIME_PACK)//时间策略	DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                                                                               .commentDate("yyyy-MM-dd")//注释日期	默认值: yyyy-MM-dd
                                                                               .build();

    /**
     * 包名配置
     */
    public static final PackageConfig PACKAGE_CONFIG = new PackageConfig.Builder().parent("com.baomidou.mybatisplus.samples.generator")//父包名
                                                                                  .moduleName("sys")//父模块名
                                                                                  .entity("po")//entity包名
                                                                                  .service("service")//service包名
                                                                                  .serviceImpl("service.impl")//service实现类包名
                                                                                  .mapper("mapper")//mapper包名
                                                                                  .xml("mapper.xml")//mapper xml文件包名
                                                                                  .controller("controller")//controller包名
                                                                                  .other("other")//自定义文件包名    输出自定义文件时所用到的包名
                                                                                  .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://"))//路径配置信息
                                                                                  .build();

    /**
     * 模板配置
     */
    public static final TemplateConfig TEMPLATE_CONFIG = new TemplateConfig.Builder().disable()//禁用所有模板
                                                                                     .disable(TemplateType.ENTITY)//禁用特定模板
                                                                                     .entity("/templates/entity.java")//设置实体模板路径(JAVA)
                                                                                     .service("/templates/service.java")//设置service模板路径(JAVA)
                                                                                     .serviceImpl("/templates/serviceImpl.java")//设置serviceImpl模板路径(JAVA)
                                                                                     .mapper("/templates/mapper.java")//设置mapper模板路径(JAVA)
                                                                                     .mapperXml("/templates/mapper.xml")//设置mapper.xml模板路径(JAVA)
                                                                                     .controller("/templates/controller.java")//设置controller模板路径(JAVA)
                                                                                     .build();

    /**
     * 注入配置
     */
    public static final InjectionConfig INJECTION_CONFIG = new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
                                                                                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                                                                                        })//输出文件之前消费者
                                                                                        .customMap(Collections.singletonMap("test", "baomidou"))//自定义配置 Map 对象
                                                                                        .customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))//自定义配置模板文件
                                                                                        .build();

    /**
     * 策略配置
     */
    public static final StrategyConfig STRATEGY_CONFIG = new StrategyConfig.Builder().enableCapitalMode()//开启大写命名 默认false
                                                                                     .enableSkipView()//开启跳过视图 默认false
                                                                                     .disableSqlFilter()//禁用 sql 过滤	默认值:true 部份数据库sql语法不能支持使用 sql 过滤表的话，可以考虑关闭此开关
                                                                                     .enableSchema()//启用 schema	默认值:false，多 schema 场景的时候打开
                                                                                     .likeTable(new LikeTable(""))//模糊表匹配(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
                                                                                     .notLikeTable(new LikeTable(""))//模糊表排除(sql 过滤)	likeTable 与 notLikeTable 只能配置一项
                                                                                     .likeTable(new LikeTable("USER")).addInclude("t_simple")//增加表匹配(内存过滤)	include 与 exclude 只能配置一项
                                                                                     .addExclude("")//增加表排除匹配(内存过滤)	include 与 exclude 只能配置一项
                                                                                     .addTablePrefix("t_", "c_")//增加过滤表前缀
                                                                                     .addTableSuffix("")//增加过滤表后缀
                                                                                     .addFieldPrefix("_flag")//增加过滤字段前缀
                                                                                     .addFieldSuffix("_flag")//增加过滤字段后缀
                                                                                     .entityBuilder()//实体策略配置
                                                                                     .controllerBuilder()//controller策略配置
                                                                                     .serviceBuilder()//service策略配置
                                                                                     .mapperBuilder()//mapper策略配置
                                                                                     .build();

    public static final StrategyConfig STRATEGY_CONFIG_ENTITY = new StrategyConfig.Builder().entityBuilder()
                                                                                            //.superClass(BaseEntity.class)//父类配置
                                                                                            //.superClass("com.baomidou.BaseEntity")//父类配置
                                                                                            .disableSerialVersionUID()// 禁用生成 serialVersionUID	默认值:true
                                                                                            .enableColumnConstant()//开启生成字段常量	默认值:false
                                                                                            .enableChainModel()//	开启链式模型	默认值:false
                                                                                            .enableLombok()//开启 lombok 模型	默认值:false
                                                                                            .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀	默认值:false
                                                                                            .enableTableFieldAnnotation()//开启生成实体时生成字段注解	默认值:false
                                                                                            .enableActiveRecord()//开启 ActiveRecord 模型	默认值:false
                                                                                            .versionColumnName("version")//乐观锁字段名(数据库)
                                                                                            .versionPropertyName("version")//乐观锁属性名(实体)
                                                                                            .logicDeleteColumnName("deleted")//逻辑删除字段名(数据库)
                                                                                            .logicDeletePropertyName("deleteFlag")//逻辑删除属性名(实体)
                                                                                            .naming(NamingStrategy.no_change)//数据库表映射到实体的命名策略	默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                                                                                            .columnNaming(NamingStrategy.underline_to_camel)//数据库表字段映射到实体的命名策略	默认为 null，未指定按照 naming 执行
                                                                                            .addSuperEntityColumns("id",
                                                                                                                   "created_by",
                                                                                                                   "created_time",
                                                                                                                   "updated_by",
                                                                                                                   "updated_time",
                                                                                                                   "remark")//添加父类公共字段
                                                                                            .addIgnoreColumns("age")//添加忽略字段
                                                                                            .addTableFills(new Column("create_time", FieldFill.INSERT))//添加表字段填充
                                                                                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))//添加实体字段填充
                                                                                            .idType(IdType.AUTO)//添加表字段填充
                                                                                            //.convertFileName("")//转换文件名称
                                                                                            .formatFileName("%sEntity")//格式化文件名称
                                                                                            .build();

    public static final StrategyConfig STRATEGY_CONFIG_CONTROLLER = new StrategyConfig.Builder().controllerBuilder()
                                                                                                //.superClass(BaseController.class)
                                                                                                //.superClass("com.baomidou.BaseController")
                                                                                                .enableHyphenStyle()//开启驼峰转连字符	默认值:false
                                                                                                .enableRestStyle()//开启生成@RestController 控制器	默认值:false
                                                                                                //.convertFileName()//转换文件名称
                                                                                                .formatFileName("%sAction")//转换文件名称
                                                                                                .build();

    public static final StrategyConfig STRATEGY_CONFIG_SERVICE = new StrategyConfig.Builder().serviceBuilder()
                                                                                             //.superServiceClass(BaseService.class)
                                                                                             //.superServiceClass("com.baomidou.BaseService")
                                                                                             //.superServiceImplClass(BaseServiceImpl.class)
                                                                                             //.superServiceClass("com.baomidou.BaseServiceImpl")
                                                                                             //.convertServiceFileName()
                                                                                             //.convertServiceImplFileName()
                                                                                             .formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp").build();

    /**
     * 策略配置——mapperBuilder()配置
     */
    public static final StrategyConfig STRATEGY_CONFIG_MAPPER = new StrategyConfig.Builder().mapperBuilder().superClass(BaseMapper.class)//父类配置
                                                                                            .superClass("com.baomidou.BaseMapper")//父类配置
                                                                                            .enableMapperAnnotation()//开启 @Mapper 注解	默认值:false
                                                                                            .enableBaseResultMap()//启用 BaseResultMap生成	默认值:false
                                                                                            .enableBaseColumnList()//启用 BaseColumnList生成	默认值:false
                                                                                            //.cache(MyMapperCache.class)//设置缓存实现类 自定义实现cache(Class<? extends Cache>)
                                                                                            //.convertMapperFileName(ConverterFileName)//转换 mapper 类文件名称
                                                                                            //.convertXmlFileName("")//转换 xml 文件名称
                                                                                            .formatMapperFileName("%sDao")//格式化 mapper 文件名称
                                                                                            .formatXmlFileName("%sXml")//格式化 mapperXml 文件名称
                                                                                            .build();

}
