# 数据库文档生成

## 添加依赖
```xml
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.30</version>
</dependency>
<!-- https://mvnrepository.com/artifact/cn.smallbun.screw/screw-core -->
<dependency>
    <groupId>cn.smallbun.screw</groupId>
    <artifactId>screw-core</artifactId>
    <version>1.0.5</version>
</dependency>
```
## 配置数据库信息在配置文件之中
```yml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3310/java_cloud?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true
    username: root
    password: 123456
```

## 测试类
```java
@SpringBootTest
class ScrewApplicationTests {

   @Autowired
   ApplicationContext applicationContext;

   @Test
   void contextLoads() {
      DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

      // 生成文件配置
      EngineConfig engineConfig = EngineConfig.builder()
            // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
            .fileOutputDir("/Users/mac/Desktop")
            // 打开目录
            .openOutputDir(false)
            // 文件类型
            .fileType(EngineFileType.HTML)
            // 生成模板实现
            .produceType(EngineTemplateType.freemarker).build();

      // 生成文档配置（包含以下自定义版本号、描述等配置连接）
      Configuration config = Configuration.builder()
            .version("1.0.3")
            .description("生成文档信息描述")
            .dataSource(dataSourceMysql)
            .engineConfig(engineConfig)
            .produceConfig(getProcessConfig())
            .build();

      // 执行生成
      new DocumentationExecute(config).execute();
   }


   /**
    * 配置想要生成的表+ 配置想要忽略的表
    * @return 生成表配置
    */
   public static ProcessConfig getProcessConfig(){
      // 忽略表名
      List<String> ignoreTableName = Arrays.asList("aa","test_group");
      // 忽略表前缀，如忽略a开头的数据库表
      List<String> ignorePrefix = Arrays.asList("a","t");
      // 忽略表后缀
      List<String> ignoreSuffix = Arrays.asList("_test","czb_");

      return ProcessConfig.builder()
            //根据名称指定表生成
            .designatedTableName(new ArrayList<>())
            //根据表前缀生成
            .designatedTablePrefix(new ArrayList<>())
            //根据表后缀生成
            .designatedTableSuffix(new ArrayList<>())
            //忽略表名
            .ignoreTableName(ignoreTableName)
            //忽略表前缀
            .ignoreTablePrefix(ignorePrefix)
            //忽略表后缀
            .ignoreTableSuffix(ignoreSuffix).build();
   }
}
```