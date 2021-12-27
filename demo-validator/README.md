# 一、参数校验

在开发中经常需要写一些字段校验的代码，比如字段非空，字段长度限制，邮箱格式验证等等，写这些与业务逻辑关系不大的代码个人感觉有两个麻烦：

- 验证代码繁琐，重复劳动
- 方法内代码显得冗长
- 每次要看哪些参数验证是否完整，需要去翻阅验证逻辑代码

hibernate-validator（[官方文档](http://hibernate.org/validator/documentation/)）提供了一套比较完善、便捷的验证实现方式。**。**
SpringBoot中集成参数校验 导入依赖

- spring版本大于2.3

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>

```

- spring版本小于等于2.3

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

注：从springboot-2.3开始，校验包被独立成了一个starter组件，所以需要引入validation和web，而springboot-2.3之前的版本只需要引入 web 依赖就可以了。

## 简单案例演示：

### 实体类

```java

@Data
public class User {

    private String id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 8, message = "用户名长度不在4-8之间")
    private String name;

    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "[0-9]\\d+", message = "密码不符合规范")
    private String password;

    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 140, message = "年龄值不太正常")
    private Integer age;

    @Max(value = 10, message = "级别超过最大值了")
    @Min(value = 1, message = "级别低于最小值")
    private Integer level;

    private String mobile;
}
```

### 封装返回类

参考：[结果集封装][1]

### 接口校验：

```java

@RestController
@RequestMapping("/user")
public class UserController {
    //创建用户
    @PostMapping
    public ApiResponse<User> register(@Valid @RequestBody User user, BindingResult errors) {
        //判断传入用户数据是否合法
        List<String> errs = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                errs.add(fieldError.getDefaultMessage());
            });
            return new ApiResponse<User>(false, null, errs);
        }
        //输入入库
        System.out.println("数据插入成功");
        return new ApiResponse<User>(false, user, errs);
    }

    //修改用户
    @PutMapping
    public ApiResponse<User> update(@Valid @RequestBody User user, BindingResult errors) {
        //判断传入用户数据是否合法
        List<String> errs = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                errs.add(fieldError.getDefaultMessage());
            });
            return new ApiResponse<>(false, null, errs);
        }
        //输入入库
        System.out.println("数据修改成功");
        return new ApiResponse<>(false, user, errs);
    }
}
```

# 二、校验模式

## 1、普通模式（默认）

会交验完所有属性，然后再返回所有的验证失败信息

## 2、快速失败返回模式

只要有一个验证失败，就停止验证，直接返回信息

## 配置方式：

关键代码如下 配置类

```java

@Configuration
public class ValidatorConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}
```

# 三、请求参数校验

## 1、请求参数校验

在@RequestBody DemoModel demo之间加注解** @Valid**，然后后面加BindindResult即可； 校验过程中多个参数的，可以加多个@Valid和BindingResult，如：

```java
public void test()(@RequestBody @Valid DemoModel demo,BindingResult result)

public void test()(@RequestBody @Valid DemoModel demo,BindingResult result,@RequestBody @Valid DemoModel demo2,BindingResult result2)
```

## 2、GET参数校验(@RequestParam参数校验)

使用校验bean的方式，没有办法校验RequestParam的内容，一般在处理Get请求(或参数比较少)的时候，会使用下面这样的代码：

```java
@GetMapping("/getById")
public Result getById(@RequestParam(name = "id") String id,@RequestParam(name = "age") Integer age){
        System.out.println("数据查询成功："+id+"年龄："+age);
        return Result.success();
        }
```

用**@Valid**注解，对RequestParam对应的参数进行注解是无效的，需要使用**@Validated**注解来使得验证生效。如下所示：

### a.此时需要使用MethodValidationPostProcessor 的Bean

在配置类中添加MethodValidationPostProcessor

```java
@Bean
public MethodValidationPostProcessor methodValidationPostProcessor(){
        //默认是普通模式，会返回所有的验证不通过信息集合
        return new MethodValidationPostProcessor();
        }
```

或者对MethodValidationPostProcessor 进行设置Validator

```java
    @Bean
public Validator validator(){
        ValidatorFactory validatorFactory=Validation.byDefaultProvider().configure().addProperty("hibernate.validator.fail_fast","true").buildValidatorFactory();

        return validatorFactory.getValidator();
        }

@Bean
public MethodValidationPostProcessor methodValidationPostProcessor(){
        //默认是普通模式，会返回所有的验证不通过信息集合
        MethodValidationPostProcessor processor=new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
        }
```

### b.方法所在的Controller上加注解@Validated

```java
@GetMapping("/getById2") @Validated public Result getById2(@NotBlank(message = "ID不能为空") @RequestParam(name = "id") String id,@Range(min = 0, max = 120, message = "年龄最小为0，最大为120") @RequestParam(name = "age") Integer age){System.out.println("方法所在的Controller上加注解@Validated");System.out.println("数据查询成功："+id+"年龄："+age);return Result.success();}
```

### c.返回验证信息提示

验证不通过时，抛出了ConstraintViolationException异常，使用统一捕获异常处理：

参考：[https://www.yuque.com/docs/share/4fbd76de-42f9-45e1-82f1-644ba84abaf3?#](https://www.yuque.com/docs/share/4fbd76de-42f9-45e1-82f1-644ba84abaf3?#)
《统一异常处理》 ​

## 3、model校验

```java

@Datapublic
class Demo2 {
    @Length(min = 5, max = 17, message = "length长度在[5,17]之间")
    private String length;
    /**     * Size 不能验证Integer，适用于String, Collection, Map and arrays	 */
    @Size(min = 1, max = 3, message = "size在[1,3]之间")
    private String age;
    @Range(min = 150, max = 250, message = "range在[150,250]之间")
    private int high;
    @Size(min = 3, max = 5, message = "list的Size在[3,5]")
    private List<String> list;
}
```

接口：

```java
    @Resource private Validator validator;@GetMapping("/demo2") public void demo2(){Demo2 demo2=new Demo2();demo2.setAge("111");demo2.setHigh(150);demo2.setLength("ABCDE");demo2.setList(new ArrayList<String>(){{add("111");add("222");add("333");}});Set<ConstraintViolation<Demo2>>violationSet=validator.validate(demo2);for(ConstraintViolation<Demo2> model:violationSet){System.out.println(model.getMessage());}}
```

## 4、对象级联校验

对象内部包含另一个对象作为属性，属性上加@Valid，可以验证作为属性的对象内部的验证：

```java

@Datapublic
class Demo2 {
    @Length(min = 5, max = 17, message = "length长度在[5,17]之间")
    private String length;
    /**     * Size 不能验证Integer，适用于String, Collection, Map and arrays	 */
    @Size(min = 1, max = 3, message = "size在[1,3]之间")
    private String age;
    @Range(min = 150, max = 250, message = "range在[150,250]之间")
    private int high;
    @Size(min = 3, max = 5, message = "list的Size在[3,5]")
    private List<String> list;
    @Length(min = 5, max = 17, message = "length长度在[5,17]之间")
    private String extField;
}

/*********************************************************************/
@Datapublic
class Demo3 {
    @Size(min = 3, max = 5, message = "list的Size在[3,5]")
    private List<String> list;
    @NotNull
    @Valid
    private Demo2 demo2;
}
```

校验：

```java
    Resource private Validator validator;@GetMapping("/demo3") public void demo3(){Demo2 demo2=new Demo2();demo2.setExtField("22");Demo3 demo3=new Demo3();demo3.setList(new ArrayList<String>(){{add("111");add("222");add("333");}});demo3.setDemo2(demo2);Set<ConstraintViolation<Demo2>>violationSet=validator.validate(demo2);for(ConstraintViolation<Demo2> model:violationSet){System.out.println(model.getMessage());}}
```

## 5、分组校验

### a、分组

有这样一种场景，新增用户信息的时候，不需要验证userId（因为系统生成）； 修改的时候需要验证userId，这时候用到validator的分组验证功能。 设置validator为普通验证模式（**"
hibernate.validator.fail_fast", "false"**），用到的验证GroupA、GroupB和model：

```java
public interface GroupA extends Default {}

public interface GroupB extends Default {}
```

实体类：

```java

@Datapublic
class Person {
    @NotBlank
    @Range(min = 1, max = Integer.MAX_VALUE, message = "必须大于0", groups = {GroupA.class})
    /**用户id*/ private Integer userId;
    @NotBlank
    @Length(min = 4, max = 20, message = "必须在[4,20]", groups = {GroupB.class})
    /**用户名*/ private String userName;
    @NotBlank
    @Range(min = 0, max = 100, message = "年龄必须在[0,100]", groups = {Default.class})
    /**年龄*/ private Integer age;
    @Range(min = 0, max = 2, message = "性别必须在[0,2]", groups = {GroupB.class})
    /**性别 0：未知；1：男；2：女*/ private Integer sex;
}
```

如上Person所示，3个分组分别验证字段如下：

- GroupA验证字段userId；
- GroupB验证字段userName、sex；
- Default验证字段age(Default是Validator自带的默认分组)

校验：

```java
@GetMapping("/demo5") public void demo5(){Person p=new Person();        //GroupA验证不通过		p.setUserId(-12);		//GroupA验证通过		//p.setUserId(12);		p.setUserName("a");		p.setAge(110);		p.setSex(5);		Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupA.class, GroupB.class);		for (ConstraintViolation<Person> item : validate) {			System.out.println(item);		}	}
```

或者使用

```java
    @GetMapping("/demo6") public void demo6(@Validated({GroupA.class, GroupB.class}) Person p,BindingResult result){if(result.hasErrors()){List<ObjectError> allErrors=result.getAllErrors();for(ObjectError error:allErrors){System.out.println(error);}}}
```

### b、组序列

除了按组指定是否验证之外，还可以指定组的验证顺序，前面组验证不通过的，后面组不进行验证： 指定组的序列（GroupA》GroupB》Default）：

```java

@GroupSequence({GroupA.class, GroupB.class, Default.class})
public interface GroupOrder {}
```

校验：

```java
@GetMapping("/demo7") public void demo7(){Person p=new Person();        //GroupA验证不通过		//p.setUserId(-12);		//GroupA验证通过		p.setUserId(12);		p.setUserName("a");		p.setAge(110);		p.setSex(5);		Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupOrder.class);		for (ConstraintViolation<Person> item : validate) {			System.out.println(item);		}	}	@GetMapping("/demo8")	public void demo8(@Validated({GroupOrder.class}) Person p, BindingResult result){		if(result.hasErrors()){			List<ObjectError> allErrors = result.getAllErrors();			for (ObjectError error : allErrors) {				System.out.println(error);			}		}	}
```

**结论：分组顺序校验时，按指定的分组先后顺序进行验证，前面的验证不通过，后面的分组就不行验证。**

# 三、分组

在上面的例子中，我们定义了user的两个方法一个添加用户， 一个是修改用户，正常来说当添加用户操作时，此时的用户id属性应该为空, 一个是修改用户，而当用户处理修改状态时，用户的id应该已经存在，

因此我们对id在两种不同的情况下应该区别对待， 如何校验用户的id呢，这时我们可以使用分组实现. 具体操作如下：

## 1、建立分组

```java
//添加用户分组public interface AddUserGroup extends Default {}//更新用户分组public interface UpdateUserGroup extends Default {}
```

## 2、实体属性添加分组属性

```java
public class User {	...
    @NotNull(message = "id不能为空", groups = {UpdateUserGroup.class})
    private String id;        ...
}   
```

## 3、接口校验添加分组

```java
public class UserController { 	...    //修改用户    @PutMapping    public ApiResponse<User> update(@Validated(UpdateUserGroup.class) @RequestBody User user, BindingResult errors) {       ...    }}
```

# 四、自定义校验的使用

当我们使用内置的校验应该无法满足需求时，我们可以实现validator的接口，自定义自己需要的验证器。 定义自定义约束，有三个步骤

- 创建约束注解
- 实现一个验证器
- 定义默认的错误信息

例如User实体的Mobile字段

## 1、创建约束注解

```java

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {MobileValidator.class})
@Retention(RUNTIME)
@Repeatable(Mobile.List.class)
public @interface Mobile {
    /**     * 错误提示信息，可以写死,也可以填写国际化的key     */
    String message() default "手机号码不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Mobile[] value();
    }
}
```

## 2、实现验证器

```java
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    /**     * 手机验证规则     */
    private Pattern pattern;

    @Override
    public void initialize(Mobile mobile) {pattern = Pattern.compile(mobile.regexp());}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {return true;}
        return pattern.matcher(value).matches();
    }
}
```

## 3、定义默认错误信息

```java
public class User {   ....
    @Mobile(message = "手机号码格式异常")
    private String mobile;
}
```

# 五、在全局校验中增加校验异常

`MethodArgumentNotValidException`是springBoot中进行绑定参数校验时的异常,需要在springBoot中处理, 其他需要 处理ConstraintViolationException异常进行处理.

- 为了优雅一点,我们将参数异常,业务异常,统一做了一个全局异常,将控制层的异常包装到我们自定义的异常中
- 为了优雅一点,我们还做了一个统一的结构体,将请求的code,和msg,data一起统一封装到结构体中,增加了代码的复用性

# 常用注解

| 注解                                                         | 属性                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `@Null`                                                      | 被注释的元素必须为 null                                      |
| `@NotNull`                                                   | 被注释的元素必须不为 null                                    |
| `@AssertTrue`                                                | 被注释的元素必须为 true                                      |
| `@AssertFalse`                                               | 被注释的元素必须为 false                                     |
| `@Min(value=)`                                               | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值     |
| `@Max(value=)`                                               | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值     |
| `@DecimalMin(value=, inclusive=)`                            | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值     |
| `@DecimalMax(value=, inclusive=)`                            | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值     |
| `@Future`                                                    | 检查被注释的日期是否是将来的日期                             |
| `@FutureOrPresent`                                           | 检查被注释的日期是现在还是将来                               |
| `@NotEmpty`                                                  | 检查被注释的元素是否不为null或为空                           |
| `@Negative`                                                  | 检查被注释的元素是否严格为负。零值被视为无效。               |
| `@NegativeOrZero`                                            | 检查被注释的元素是负数还是零。                               |
| `@Past`                                                      | 检查被注释的日期是否是过去的日期                             |
| `@PastOrPresent`                                             | 检查被注释的日期是过去还是现在                               |
| `@Pattern(regex=, flags=)`                                   | 检查被注释的字符串是否与正则表达式匹配`match`                |
| `@Positive`                                                  | 检查被注释元素是否严格为正。零值被视为无效。                 |
| `@PositiveOrZero`                                            | 检查被注释元素是正数还是零。                                 |
| `@Size(min=, max=)`                                          | 检查被注释的元素的大小是否介于`min`                          |
| 和之间`max`                                                  |                                                              |
| （包括）                                                     |                                                              |
| `@Email`                                                     | 检查被注释的元素是否为有效的电子邮件地址。                   |
| `@Digits(integer=, fraction=)`                               | 被注释的元素必须是一个数字，其值必须在可接受的范围内         |
| `@Currency(value=)`                                          | 检查带注释的货币单位`javax.money.MonetaryAmount`             |
| 是否为指定货币单位的一部分。                                 |                                                              |
| `@ISBN`                                                      | 检查带注释的字符序列是有效的[ISBN](https://en.wikipedia.org/wiki/International_Standard_Book_Number) |
| 。`type`                                                     |                                                              |
| 确定ISBN的类型。默认值为ISBN-13。                            |                                                              |
| `@Length(min=, max=)`                                        | 被注释的字符串的大小必须在指定的范围内                       |
| `@Range(min=, max=)`                                         | 被注释的元素必须在合适的范围内                               |
| `@SafeHtml(whitelistType= , additionalTags=, additionalTagsWithAttributes=, baseURI=)` | 检查带注释的值是否包含潜在的恶意片段，例如`<script/>`        |
| 。为了使用此约束，[jsoup](http://jsoup.org/)                 |                                                              |
| 库必须是类路径的一部分。通过该`whitelistType`                |                                                              |
| 属性，可以选择预定义的白名单类型，可以通过`additionalTags`   |                                                              |
| 或进行细化`additionalTagsWithAttributes`                     |                                                              |
| 。前者允许添加没有任何属性的标签，而后者则允许使用注释指定标签和可选的允许属性以及该属性的可接受协议`@SafeHtml.Tag` |                                                              |
| 。另外，`baseURI`                                            |                                                              |
| 允许指定用于解析相对URI的基本URI。                           |                                                              |
| `@UniqueElements`                                            | 检查被注释的集合仅包含唯一元素                               |
| `@URL(protocol=, host=, port=, regexp=, flags=)`             | 根据RFC2396检查带注释的字符序列是否为有效URL。如果任何可选参数`protocol` |
| ，`host`                                                     |                                                              |
| 或`port`                                                     |                                                              |
| 指定时，相应的URL片段必须在指定的值相匹配。可选参数，`regexp` |                                                              |
| 并`flags`                                                    |                                                              |
| 允许指定URL必须匹配的其他正则表达式（包括正则表达式标志）。默认情况下，此约束使用`java.net.URL` |                                                              |
| 构造函数来验证给定的字符串是否表示有效的URL。也提供基于正则表达式的版本`RegexpURLValidator` |                                                              |
| -可以通过XML（请参见[第8.2节“通过映射约束](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-mapping-xml-constraints) |                                                              |
| `[constraint-mappings](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-mapping-xml-constraints)` |                                                              |
| [”](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-mapping-xml-constraints) |                                                              |
| ）或编程API（请参见[第12.14.2节“以编程方式添加约束定义”](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-programmatic-constraint-definition) |                                                              |
| ）进行配置。                                                 |                                                              |

---

# 附录：

参考：[https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-provider-specific-settings](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-provider-specific-settings)

[1]:https://www.yuque.com/docs/share/49b5bbab-3c93-4ffb-9c33-c3e5e6087911?# 《统一结果集封装（新）》
