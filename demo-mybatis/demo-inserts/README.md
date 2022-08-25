## 数据库
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
# 批量插入的方式
## Mybatis 自带批量插入
```java
insertsService.saveBatch(list);
```
## JDBCTemplate
```java
  String insertSql = "insert into demo_mybatis_inserts(username, password, _no) values (?,?,?)";
  jdbcTemplate.batchUpdate(insertSql, list);
```
## 自定义Mybatis批量插入
这种实现方式要特别注意数据库SQL语句的长度限制，在进行数据合并在同一SQL中务必不能超过SQL长度限制，通过 max_allowed_packet 配置可以修改，默认是1M，测试时修改为8M
最终的执行效果为：
```java
INSERT INTO demo_mybatis_inserts(username,password,_no)
        values
        <foreach collection="list" item="item" index="index" separator=",">
            (#{item.username},
            #{item.password},
            #{item.No})
        </foreach>

```