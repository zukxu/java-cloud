# 隐私数据脱敏

## 1、引入依赖

```xml
 <!--jasypt加解密-->
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

## 2、加解密配置

配置文件加入秘钥配置项jasypt.encryptor.password，  
并将需要脱敏的value值替换成预先经过加密的内容ENC(加密后的字符串)  
例如：

```yaml
jasypt:
  encryptor:
    password: asdaszdsad
```
## 3、api调用
```java
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("jasypt测试")
public class Jasypt {
    @Autowired
    private StringEncryptor stringEncryptor;
    @Test
    void encryptPwd() {
        //加密
        String username = stringEncryptor.encrypt(" root ");
        System.out.println("加密username:" + username);
        String decUsername = stringEncryptor.decrypt(username);
        System.out.println("解密username:" + decUsername);
        //加密
        String password = stringEncryptor.encrypt(" 123456 ");
        System.out.println("password:" + password);
    }
}
```
## 4、使用加密
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db1?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&ze oDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai
    username: root
    password: ENC(mVTvp4IddqdaYGqPl9lCQbzM3H/b0B6l)
```

## 5、自定义加密写法
**ENC()**是默认的写法，我们可以进行定义并自定义格式

```yaml
jasypt:
  encryptor:
    property:
      prefix: "abc["
      suffix: "]"
```

## 参数注入启动

秘钥是个安全性要求比较高的属性，所以一般不建议直接放在项目内，可以通过启动时-D参数注入，或者放在配置中心，避免泄露

```shell
java -jar -Djasypt.encryptor.password=1123 jasypt.jar
```