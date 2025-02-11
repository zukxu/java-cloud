### **1. 前言**

Apache Shiro是一个功能强大且易于使用的Java安全框架，提供了认证，授权，加密，和会话管理。
![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158280-9674698a-f49f-471e-bb2e-84994e0b0bdf.png#align=left&display=inline&height=506&margin=%5Bobject%20Object%5D&originHeight=506&originWidth=880&size=0&status=done&style=none&width=880)
Shiro有三大核心组件： Subject：即当前用户，在权限管理的应用程序里往往需要知道谁能够操作什么，谁拥有操作该程序的权利，shiro中则需要通过Subject来提供基础的当前用户信息，Subject
不仅仅代表某个用户，与当前应用交互的任何东西都是Subject，如网络爬虫等。所有的Subject都要绑定到SecurityManager上，与Subject的交互实际上是被转换为与SecurityManager的交互。
SecurityManager：即所有Subject的管理者，这是Shiro框架的核心组件，可以把他看做是一个Shiro框架的全局管理组件，用于调度各种Shiro框架的服务。作用类似于SpringMVC中的DispatcherServlet，用于拦截所有请求并进行处理。
Realm：Realm是用户的信息认证器和用户的权限人证器，我们需要自己来实现Realm来自定义的管理我们自己系统内部的权限规则。SecurityManager要验证用户，需要从Realm中获取用户。可以把Realm看做是数据源。

### **2. 数据库设计**

#### **2.1 User(用户)**

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158330-a743498c-bfe0-4693-a37e-3dd21fc11ab1.png#align=left&display=inline&height=159&margin=%5Bobject%20Object%5D&originHeight=159&originWidth=536&size=0&status=done&style=none&width=536)

```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '超级用户', 'root');
INSERT INTO `user` VALUES (2, 'user', '普通用户', 'user');
INSERT INTO `user` VALUES (3, 'vip', 'VIP用户', 'vip');
SET FOREIGN_KEY_CHECKS = 1;
```

#### **2.2 Role(角色)**

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158334-f3ad3f53-ad3d-4987-872d-acc7da1ae3d0.png#align=left&display=inline&height=171&margin=%5Bobject%20Object%5D&originHeight=171&originWidth=627&size=0&status=done&style=none&width=627)

```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '超级管理员');
INSERT INTO `role` VALUES (2, 'user', '普通用户');
INSERT INTO `role` VALUES (3, 'vip_user', 'VIP用户');
SET FOREIGN_KEY_CHECKS = 1;
```

#### **2.3 Permission(权限)**

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158319-c62a4855-28f9-482b-8289-3be98a409462.png#align=left&display=inline&height=174&margin=%5Bobject%20Object%5D&originHeight=174&originWidth=454&size=0&status=done&style=none&width=454)

```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'add', '增加');
INSERT INTO `permission` VALUES (2, 'update', '更新');
INSERT INTO `permission` VALUES (3, 'select', '查看');
INSERT INTO `permission` VALUES (4, 'delete', '删除');
SET FOREIGN_KEY_CHECKS = 1;
```

#### **2.4 User_Role(用户-角色)**

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158269-328291a7-6927-4490-ad53-66c1f34497e0.png#align=left&display=inline&height=169&margin=%5Bobject%20Object%5D&originHeight=169&originWidth=342&size=0&status=done&style=none&width=342)

```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;
-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 3);
SET FOREIGN_KEY_CHECKS = 1;
```

#### **2.5 Role_Permission(角色-权限)**

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158407-2b6b237e-ce72-40e3-b7c0-2640289275ea.png#align=left&display=inline&height=298&margin=%5Bobject%20Object%5D&originHeight=298&originWidth=453&size=0&status=done&style=none&width=453)

```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `permission_id` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;
-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 2, 3);
INSERT INTO `role_permission` VALUES (6, 3, 3);
INSERT INTO `role_permission` VALUES (7, 3, 2);
INSERT INTO `role_permission` VALUES (8, 2, 1);
SET FOREIGN_KEY_CHECKS = 1;
```

**3. 项目结构**
![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158433-a767bc2c-996b-4f13-ba7f-f8243f30060a.png#align=left&display=inline&height=709&margin=%5Bobject%20Object%5D&originHeight=709&originWidth=378&size=0&status=done&style=none&width=378)

### **4. 前期准备**

#### **4.1 导入Pom**

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.2</version>
    </dependency>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>1.4.0</version>
    </dependency>
</dependencies>
```

#### **4.2 application.yml**

```yaml
server:
  port: 8903
spring:
  application:
    name: lab-user
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/laboratory?charset=utf8
    username: root
    password: root
mybatis:
  type-aliases-package: cn.ntshare.laboratory.entity
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
```

#### **4.3 实体类**

##### 4.3.1 User.java

```java

@Data
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -6056125703075132981L;
    private Integer id;
    private String account;
    private String password;
    private String username;
}
```

##### 4.3.2 Role.java

```java

@Data
@ToString
public class Role implements Serializable {
    private static final long serialVersionUID = -1767327914553823741L;
    private Integer id;
    private String role;
    private String desc;
}
```

**4.4 Dao层**

##### 4.4.1 PermissionMapper.java

```java

@Mapper
@Repository
public interface PermissionMapper {
    List<String> findByRoleId(@Param("roleIds") List<Integer> roleIds);
}
```

##### 4.4.2 PermissionMapper.xml

```xml

<mapper namespace="cn.ntshare.laboratory.dao.PermissionMapper">
    <sql id="base_column_list">
        id, permission, desc
    </sql>
    <select id="findByRoleId" parameterType="List" resultType="String">
        select permission
        from permission, role_permission rp
        where rp.permission_id = permission.id and rp.role_id in
        <foreach collection="roleIds" item="id" open="(" close=")" separator=",">
            #{id}
        </foreach>
    </select>
</mapper>
```

##### 4.4.3 RoleMapper.java

```java

@Mapper
@Repository
public interface RoleMapper {
    List<Role> findRoleByUserId(@Param("userId") Integer userId);
}
```

##### 4.4.4 RoleMapper.xml

```xml

<mapper namespace="cn.ntshare.laboratory.dao.RoleMapper">
    <sql id="base_column_list">
        id, user_id, role_id
    </sql>
    <select id="findRoleByUserId" parameterType="Integer" resultType="Role">
        select role.id, role
        from role, user, user_role ur
        where role.id = ur.role_id and ur.user_id = user.id and user.id = #{userId}
    </select>
</mapper>
```

##### 4.4.5 UserMapper.java

```java

@Mapper
@Repository
public interface UserMapper {
    User findByAccount(@Param("account") String account);
}
```

##### 4.4.6 UserMapper.xml

```xml

<mapper namespace="cn.ntshare.laboratory.dao.UserMapper">
    <sql id="base_column_list">
        id, account, password, username
    </sql>
    <select id="findByAccount" parameterType="Map" resultType="User">
        select
        <include refid="base_column_list"/>
        from user
        where account = #{account}
    </select>
</mapper>
```

#### **4.5 Service层**

##### 4.5.1 PermissionServiceImpl.java

```java

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> findByRoleId(List<Integer> roleIds) {
        return permissionMapper.findByRoleId(roleIds);
    }
}
```

##### 4.5.2 RoleServiceImpl.java

```java

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByUserId(Integer id) {
        return roleMapper.findRoleByUserId(id);
    }
}
```

##### 4.5.3 UserServiceImpl.java

```java

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByAccount(String account) {
        return userMapper.findByAccount(account);
    }
}
```

#### **4.6. 系统返回状态枚举与包装函数**

##### 4.6.1 ServerResponseEnum.java

```java

@AllArgsConstructor
@Getter
public enum ServerResponseEnum {
    SUCCESS(0, "成功"),
    ERROR(10, "失败"),
    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    DUPLICATE_ACCOUNT(12, "账号重复"),
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    INCORRECT_CREDENTIALS(14, "账号或密码错误"),
    NOT_LOGIN_IN(15, "账号未登录"),
    UNAUTHORIZED(16, "没有权限");
    Integer code;
    String message;
}
```

##### 4.6.2 ServerResponseVO.java

```java

@Getter
@Setter
@NoArgsConstructor
public class ServerResponseVO<T> implements Serializable {
    private static final long serialVersionUID = -1005863670741860901L;
    // 响应码
    private Integer code;
    // 描述信息
    private String message;
    // 响应内容
    private T data;

    private ServerResponseVO(ServerResponseEnum responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    private ServerResponseVO(ServerResponseEnum responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    private ServerResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回成功信息
     * @param data 信息内容
     * @param <T>
     * @return
     */
    public static <T> ServerResponseVO success(T data) {
        return new ServerResponseVO<>(ServerResponseEnum.SUCCESS, data);
    }

    /**
     * 返回成功信息
     * @return
     */
    public static ServerResponseVO success() {
        return new ServerResponseVO(ServerResponseEnum.SUCCESS);
    }

    /**
     * 返回错误信息
     * @param responseCode 响应码
     * @return
     */
    public static ServerResponseVO error(ServerResponseEnum responseCode) {
        return new ServerResponseVO(responseCode);
    }
}
```

#### **4.7 统一异常处理**

当用户身份认证失败时，会抛出`UnauthorizedException`，我们可以通过统一异常处理来处理该异常

```java

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerResponseVO UnAuthorizedExceptionHandler(UnauthorizedException e) {
        return ServerResponseVO.error(ServerResponseEnum.UNAUTHORIZED);
    }
}
```

### **5. 集成Shiro**

#### **5.1 UserRealm.java**

```java
/**
 * 负责认证用户身份和对用户进行授权
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    // 用户授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.findRoleByUserId(user.getId());
        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleSet.add(role.getRole());
            roleIds.add(role.getId());
        }
        // 放入角色信息
        authorizationInfo.setRoles(roleSet);
        // 放入权限信息
        List<String> permissionList = permissionService.findByRoleId(roleIds);
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));
        return authorizationInfo;
    }

    // 用户认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        User user = userService.findByAccount(token.getUsername());
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
```

#### **5.2 ShiroConfig.java**

```java

@Configuration
public class ShiroConfig {
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 路径过滤规则
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        Map<String, String> map = new LinkedHashMap<>();
        // 有先后顺序
        map.put("/login", "anon"); // 允许匿名访问
        map.put("/**", "authc"); // 进行身份认证后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```

#### **5.3 LoginController.java**

```java
@RestController
@RequestMapping("")
public class LoginController {
    @PostMapping("/login")
    public ServerResponseVO login(@RequestParam(value = "account") String account,
                                  @RequestParam(value = "password") String password) {
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            // 登录验证
            userSubject.login(token);
            return ServerResponseVO.success();
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
    }
    @GetMapping("/login")
    public ServerResponseVO login() {
        return ServerResponseVO.error(ServerResponseEnum.NOT_LOGIN_IN);
    }
    @GetMapping("/auth")
    public String auth() {
        return "已成功登录";
    }
    @GetMapping("/role")
    @RequiresRoles("vip")
    public String role() {
        return "测试Vip角色";
    }
    @GetMapping("/permission")
    @RequiresPermissions(value = {"add", "update"}, logical = Logical.AND)
    public String permission() {
        return "测试Add和Update权限";
    }
    @GetMapping("/logout")
    public String logOut() {
      Subject userSubject = SecurityUtils.getSubject();
      userSubject.logout();
      return "注销登陆";
    }
}
```

### **6. 测试**

#### **6.1 用root用户登录**

##### 6.1.1 登录

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158357-f0a4b03b-13b0-4b41-8396-e5dc34b4d8e5.png#align=left&display=inline&height=479&margin=%5Bobject%20Object%5D&originHeight=479&originWidth=703&size=0&status=done&style=none&width=703)

##### 6.1.2 验证是否登录

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158420-40a06272-a52f-4663-a1da-51f8392fe916.png#align=left&display=inline&height=382&margin=%5Bobject%20Object%5D&originHeight=382&originWidth=817&size=0&status=done&style=none&width=817)
6.1.3
测试角色权限

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158364-8302109a-b67c-4a1d-9e12-395de5be4090.png#align=left&display=inline&height=419&margin=%5Bobject%20Object%5D&originHeight=419&originWidth=623&size=0&status=done&style=none&width=623)

##### 6.1.4 测试用户操作权限

![](https://cdn.nlark.com/yuque/0/2021/png/2687219/1619085158441-7d5f2138-3eb9-461a-9927-6dc77e7b1bdf.png#align=left&display=inline&height=416&margin=%5Bobject%20Object%5D&originHeight=416&originWidth=664&size=0&status=done&style=none&width=664)
**7. 总结**
本文演示了 Spring Boot 极简集成 Shiro 框架，实现了基础的身份认证和授权功能，如有不足，请多指教。 后续可扩展的功能点有：

1. 集成 Redis 实现 Shiro 的分布式会话
2. 集成 JWT 实现单点登录功能
