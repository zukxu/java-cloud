## 1、数据库批量插入

```sql
create table java_cloud.demo_mybatis_inserts
(
    id       int auto_increment
        primary key,
    username varchar(50) null,
    password varchar(64) null,
    _no      varchar(64) null
);


```

### 批量插入的方式

### 1、Mybatis 自带批量插入

```java
public class TestInserts {

    void test1() {
        insertsService.saveBatch(list);
    }

}
```

### 2、JDBCTemplate

```java
public class TestInserts {

    void test1() {
        String insertSql = "insert into demo_mybatis_inserts(username, password, _no) values (?,?,?)";
        jdbcTemplate.batchUpdate(insertSql, list);
    }

}
```

### 3、自定义Mybatis批量插入

这种实现方式要特别注意数据库SQL语句的长度限制，在进行数据合并在同一SQL中务必不能超过SQL长度限制，通过 max_allowed_packet 配置可以修改，默认是1M，测试时修改为8M
最终的执行效果为：

```xml

<select>
    INSERT INTO demo_mybatis_inserts(username,password,_no)
    values
    <foreach collection="list" item="item" index="index" separator=",">
        (#{item.username},
        #{item.password},
        #{item.No})
    </foreach>
</select>
```

推荐第三种方法进行使用，插入效率大幅度提升

## 2、Mybatis 流式查询

流式查询指的是查询成功后不是返回一个集合而是返回一个迭代器，应用每次从迭代器取一条查询结果。流式查询的好处是能够降低内存使用
流式查询的过程当中，数据库连接是保持打开状态的，因此要注意的是：执行一个流式查询后，数据库访问框架就不负责关闭数据库连接了，需要应用在取完数据后自己关闭。

### 1、接口类

> org.apache.ibatis.cursor.Cursor  
> isOpen()：用于在取数据之前判断 Cursor 对象是否是打开状态。只有当打开时 Cursor 才能取数据；  
> isConsumed()：用于判断查询结果是否全部取完。  
> getCurrentIndex()：返回已经获取了多少条数据

### 2、流式查询接口

通过指定 Mapper 方法的返回值为 Cursor 类型，MyBatis 就指定这个查询方法为一个流式查询
流式查询取数据时需要保持数据库连接打开，取完数据才可以关闭，所以我们推荐使用数据库连接池
以下为保持数据库连接的三种方法：

- SqlSessionFactory
  使用 SqlSession 来获得 Mapper 对象 这样才能保证得到的 Cursor 对象是打开状态的
  参考：com.zukxu.mybatis.inserts#testFluentQuery
- TransactionTemplate
  执行数据库事务，而数据库事务的内容则是调用 Mapper 对象的流式查询的结果
  参考：com.zukxu.mybatis.inserts#testFluentQuery
- @Transactional 注解
  注意该注解的使用规范，防止注解不生效的情况出现

#### 1、默认的流式查询

使用Cursor 游标进行获取数据

```java
public interface DemoMybatisInsertsMapper {

    @Select("select id, username, password, _no from demo_mybatis_inserts limit #{limit}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    Cursor<DemoMybatisInserts> scan(@Param("limit") Integer limit);

}

```

#### 2、使用注解的方式查询

select语句需要增加fetchSize属性，底层是调用jdbc的setFetchSize方法，查询时从结果集里面每次取设置的行数，循环去取，直到取完。  
默认size是0，也就是默认会一次性把结果集的数据全部取出来，当结果集数据量很大时就容易造成内存溢出
可以直接设置转换为实体，和xml中的ResultType配置相同作用
使用ResultHandler进行处理，也可以自定义处理器
**则注意： 返回类型必须为void 哦，因为在handler里处理数据，所以这个handler 也是必须的**

```java
public interface DemoMybatisInsertsMapper {

    @Select("select id, username, password, _no from demo_mybatis_inserts ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(DemoMybatisInserts.class)
    void scanHandler(@Param(Constants.WRAPPER) QueryWrapper<DemoMybatisInserts> wrapper, ResultHandler<DemoMybatisInserts> handler);

}
```

##### 2.1、自定义结果集处理器

参考：**com.zukxu.mybatis.inserts.handlerDemoResultHandler**

#### 3、使用mapper.xml中配置方法

需要增加 fetchSize="1000"配置

```xml

<select id="scanMapper" resultType="com.zukxu.mybatis.inserts.model.DemoMybatisInserts" fetchSize="1000">
    select id, username, password, _no from demo_mybatis_inserts ${ew.customSqlSegment}
</select>
```

## 附录

### 应用场景

- 大数据量查询计算
- 分库分表查询
- 大数据量导出
- ...

