简单地去理解，其实AOP要做三类事：

- 在哪里切入，也就是类似于权限校验等非业务操作在哪些业务代码中执行。
- 在什么时候切入，是业务代码执行前还是执行后。
- 切入后做什么事，比如做权限校验、日志记录等。

![image.png](https://cdn.nlark.com/yuque/0/2022/png/2687219/1649403547372-ac242b1e-d869-4b3b-af41-f14c3dd6bb73.png#clientId=ue8b7a4a2-77cb-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=601&id=u7e63e026&margin=%5Bobject%20Object%5D&name=image.png&originHeight=661&originWidth=899&originalType=binary&ratio=1&rotation=0&showTitle=false&size=45982&status=done&style=none&taskId=u9c614054-4e4b-4775-a90d-54cd5bada99&title=&width=817.2727095588184)

---

## 创建Aspect切面类

1.创建一个AOP切面类，只要在类上加个 @Aspect 注解即可。
@Aspect 注解用来描述一个切面类，定义切面类的时候需要打上这个注解。
@Component 注解将该类交给 Spring 来管理。在这个类里实现advice

### 1、切点

**在多个表达式之间使用 【 || , or 】表示 或 ，使用【 && , and 】表示 与 ，【 ！】 表示 **
**非**

#### 1.1、execution 执行表达式

**execution(* com.sample.service.impl..*.*(..))**

| **符号**                      | **含义**               |
|-----------------------------|----------------------|
| **execution（）**             | **表达式的主体；**          |
| **第一个"*"符号**                | **表示返回值的类型 ->任意类型；** |
| **com.sample.service.impl** | **AOP所切的服务的包名，**     
 **即，我们的业务部分**               |
| **包名后面的“..”**               | **表示当前包及子包**         |
| **第二个“*”**                  | **表示类名，**            

***即所有类。**
**此处可以自定义** |
| **.*(..)** | **表示任何方法名，括号表示参数，两个点表示任何参数类型** |

基本语法格式为：
execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)  
除了返回类型模式、方法名模式和参数模式外，其它项都是可选的。

**常见表达式**

##### 通过方法名定义切点

| **表达式**                                       | **含义**                                                |
|-----------------------------------------------|-------------------------------------------------------|
| execution(public * *(..))                     | 匹配所有目标类的public方法，                                     
 第一个“*”代表方法返回值类型，第二个“*(..)”代表任意方法名，".."代表任意入参； |
| execution(* *To(..))                          | 匹配目标类所有以To为后缀的方法。第一个“*”代表任意方法返回类型，而“*To”代表任意以To结尾的方法名 |

##### 通过类定义切点

| **表达式**                                                  | **含义**               |
|----------------------------------------------------------|----------------------|
| execution(* com.taotao.Waiter.*(..))                     | 匹配Waiter类中的所有方法，     
 第一个“*”代表任意返回类型，“com.taotao.Waiter.*(..)”代表Waiter接口中的所有方法 |
| execution(* com.taotao.Waiter+.*(..))                    | 匹配Waiter接口及其所有实现类的方法 
 “+”表示其实现类也会符合                                            |

##### 通过包名定义切点

**注意：在包名模式串中，"."表示包下的所有类，而“..*”表示包、子孙包下的所有类。**

| **表达式**                            | **含义**                                |
|------------------------------------|---------------------------------------|
| execution(* com.taotao.*(..))      | 匹配com.taotao包下所有类的所有方法                |
| execution(* com.taotao..*(..))     | 匹配com.taotao包及其子孙包下所有类的所有方法，          |
| execution(* com..*.*Dao.find*(..)) | 匹配以com开头的任何包名下后缀为Dao的类，并且方法名以find为前缀， 

如：
com.taotao.UserDao#findByUserId()、com.taotao.dao.ForumDao#findById()的方法都是匹配切点 |

##### 通过方法入参定义切点

切点表达式中方法入参部分比较复杂，
可以使用“*”和“…”通配符，
其中“*”表示任意类型的参数，而“..”表示任意类型参数且参数个数不限

| **表达式**                      | **含义**          |
|------------------------------|-----------------|
| execution(* joke(String, *)) | 匹配目标类中joke()方法， 

该方法第一个入参为String类型，
第二个入参可以是任意类型 |
| execution(* joke(String, int)) | 匹配类中的joke()方法，
且第一个入参为String类型，
第二个入参 为int类型 |
| execution(* joke(String, ..)) | 匹配目标类中joke()方法，
该方法第一个入参为String，
后面可以有任意个且类型不限的参数 |

##### 常见的切点表达式

**匹配方法签名**
**execution**

```java
// 匹配指定包中的所有方法
execution(* com.xys.service.*(..))

// 匹配当前包中的所有public方法
execution(public * UserService.*(..))

// 匹配指定包中的所有public方法，并且返回值是int类型的方法
execution(public int com.xys.service.*(..))

// 匹配指定包中的所有public方法，并且第一个参数是String，返回值是int类型的方法
execution(public int com.xys.service.*(String name, ..))

```

**匹配类型签名**
**within**

```java
// 匹配指定包中的所有方法，但不包括子包
within(com.xys.service.*)

// 匹配指定包中的 所有方法，包括子包
within(com.xys.service..*)

// 匹配当前包中的指定类中的方法
within(UserService)

// 匹配一个接口的所有实现类中的实现的方法
within(UserDao+)
 
//目标类(target)如果有Transactional注解中的所有方法
within(org.springframework.transaction.annotation.Transactional)

```

**匹配实现**
**this/target**

```java
//任何实现了com.xyz.service.AccountService接口中的方法
this(com.xyz.service.AccountService)
    
//任何目标对象实现了com.xyz.service.AccountService的方法
target(com.xyz.service.AccountService)
    
一般情况下代理类(Proxy)和目标类(Target)都实现了相同的接口，所以上面的2个基本是等效的
```

**匹配参数**
**args**

```java
//有且只有一个Serializable参数的方法
args(java.io.Serializable)
    

//有且仅有一个参数并且参数上类型上有Transactional注解
args(org.springframework.transaction.annotation.Transactional)

   
```

tips：**@Around(value = "args(java.lang.String)") 错误用法**

```java
args 单独使用，会导致tomcat 启动异常
基本类型和自定义类型都会抛出这个异常
Unable to start web server; 
nested exception is org.springframework.beans.factory.BeanCreationException:
Error creating bean with name 'tomcatServletWebServerFactory' defined in class path resource

    
    
//正确的用法 ：限定 aop 的范围 ，然后使用args
@Around(value = "execution(* com.service.Demo.*(..))&& args(java.lang.String)")
@Around(value = " execution(* com.service.Demo.*(..))&& args(com.vo.DoctorAdviceVO)")

```

**匹配Bean名字**
**bean**

```java
// 匹配以指定名字结尾的bean中的所有方法
bean(*Service)
    
// 匹配以Service或ServiceImpl结尾的bean
bean(*Service || *ServiceImpl)

// 匹配名字以Service结尾，并且在包com.xys.service中的Bean
bean(*Service) && within(com.xys.service.*)

```

** 匹配注解**
**annotation**

```java
    
//任何方法有Transactional注解的方法
annotation(org.springframework.transaction.annotation.Transactional)

```