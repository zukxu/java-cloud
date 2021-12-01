# Mybatis-Plus 代码生成器

## 依赖
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.3.4</version>
</dependency>

<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.5.1</version>
</dependency>
```

## 生成方法
### 1、快速生成
核心代码
```java
FastAutoGenerator.create("url", "username", "password")
	.globalConfig(builder -> {
		builder.author("baomidou") // 设置作者
            .enableSwagger() // 开启 swagger 模式
			.fileOverride() // 覆盖已生成文件
			.outputDir("D://"); // 指定输出目录
	})
	.packageConfig(builder -> {
		builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
			.moduleName("system") // 设置父包模块名
            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://")); // 设置mapperXml生成路径
	})
	.strategyConfig(builder -> {
		builder.addInclude("t_simple") // 设置需要生成的表名
			.addTablePrefix("t_", "c_"); // 设置过滤表前缀
	})
	.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
	.execute();
```
### 2、交互式生成
```java
FastAutoGenerator.create("url", "username", "password")
        .globalConfig(builder -> {
        builder.author("baomidou") // 设置作者
        .enableSwagger() // 开启 swagger 模式
        .fileOverride() // 覆盖已生成文件
        .outputDir("D://"); // 指定输出目录
        })
        .packageConfig(builder -> {
        builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
        .moduleName("system") // 设置父包模块名
        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://")); // 设置mapperXml生成路径
        })
        .strategyConfig(builder -> {
        builder.addInclude("t_simple") // 设置需要生成的表名
        .addTablePrefix("t_", "c_"); // 设置过滤表前缀
        })
        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();
```
