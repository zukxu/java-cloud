## 第一种

第一种就是Springboot提供的actuator的功能，它可以执行shutdown, health, info等，默认情况下，actuator的shutdown是disable的，我们需要打开它。

导入依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

核心代码

```java
SpringApplication.run(ElegantDowntimeApplication.class);
```

请求 在请求连接中使用/shutdown方法即可进行优雅停机

## 第二种

第二种方法也比较简单，获取程序启动时候的context，然后关闭主程序启动时的context。这样程序在关闭的时候也会调用PreDestroy注解。

核心代码

```java
ConfigurableApplicationContext ctx=SpringApplication.run(ElegantDowntimeApplication.class,args);

        try{
        System.out.println("10s 后自动停止");
        TimeUnit.SECONDS.sleep(10);

        }catch(InterruptedException e){
        e.printStackTrace();
        }
        ctx.close();
```

模拟10s后进行关闭

## 第三种

使用shell脚本进行停止，原理为将程序运行的pid进行记录

核心代码

```java
    SpringApplication application=new SpringApplication(ElegantDowntimeApplication.class);
        application.addListeners(new ApplicationPidFileWriter("/Users/demo/app.pid"));
        application.run();
```

## 第四种

通过调用一个SpringApplication.exit()方法进行退出程序

核心代码

```java
public static void main(String[]args){
        ConfigurableApplicationContext ctx=SpringApplication.run(ElegantDowntimeApplication.class,args);
        exitApplication(ctx);
        }
public static void exitApplication(ConfigurableApplicationContext context){
        int exitCode=SpringApplication.exit(context,(ExitCodeGenerator)()->0);

        System.exit(exitCode);
        }
```
