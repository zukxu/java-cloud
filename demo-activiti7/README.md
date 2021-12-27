# activiti7学习

## 了解BPMN2.0 
## 整合SpringBoot
1. 导入依赖
```xml
<!-- https://mvnrepository.com/artifact/org.activiti/activiti-spring-boot-starter -->
<dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-spring-boot-starter</artifactId>
    <version>7.1.0.M6</version>
</dependency>

```
2. 创建数据库，生成表结构
```java
    /**
     * 生成activiti 的数据库表
     */
    @Test
    void createTable() {
        //默认创建方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通用创建方式,指定文件名进行创建
        //ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration");
        //processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }
```
3. 执行流程启动过程

1、TestDeployment  
2、TestStartProcess  
3、TestTaskList  
4、TestQueryProcessDefinition
5、TestDelDeploy