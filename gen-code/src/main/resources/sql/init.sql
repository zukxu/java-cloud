create database `mybatis-plus`;

use `mybatis-plus`;

-- auto-generated definition
create table gen_table
(
    table_id          bigint auto_increment comment '编号'
        primary key,
    table_name        varchar(200) default ''     null comment '表名称',
    table_comment     varchar(500) default ''     null comment '表描述',
    sub_table_name    varchar(64)                 null comment '关联子表的表名',
    sub_table_fk_name varchar(64)                 null comment '子表关联的外键名',
    class_name        varchar(100) default ''     null comment '实体类名称',
    tpl_category      varchar(200) default 'crud' null comment '使用的模板（crud单表操作 tree树表操作）',
    package_name      varchar(100)                null comment '生成包路径',
    module_name       varchar(30)                 null comment '生成模块名',
    business_name     varchar(30)                 null comment '生成业务名',
    function_name     varchar(50)                 null comment '生成功能名',
    function_author   varchar(50)                 null comment '生成功能作者',
    gen_type          char         default '0'    null comment '生成代码方式（0zip压缩包 1自定义路径）',
    gen_path          varchar(200) default '/'    null comment '生成路径（不填默认项目路径）',
    options           varchar(1000)               null comment '其它生成选项',
    create_by         varchar(64)  default ''     null comment '创建者',
    create_time       datetime                    null comment '创建时间',
    update_by         varchar(64)  default ''     null comment '更新者',
    update_time       datetime                    null comment '更新时间',
    remark            varchar(500)                null comment '备注'
)
    comment '代码生成业务表' charset = utf8;

INSERT INTO `gen_table` VALUES (21, 'sys_menu', '菜单权限表', NULL, NULL, 'SysMenu', 'crud', 'com.ruoyi.system', 'system', 'menu', '菜单权限', 'ruoyi', '0', '/', NULL, 'tony', '2021-08-12 15:49:51', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (28, 'sys_user', '用户信息表', NULL, NULL, 'SysUser', 'crud', 'com.ruoyi.system', 'system', 'user', '用户信息', 'ruoyi', '0', '/', NULL, 'tony', '2021-08-12 15:49:51', '', NULL, NULL);

-- auto-generated definition
create table gen_table_column
(
    column_id      bigint auto_increment comment '编号'
        primary key,
    table_id       varchar(64)               null comment '归属表编号',
    column_name    varchar(200)              null comment '列名称',
    column_comment varchar(500)              null comment '列描述',
    column_type    varchar(100)              null comment '列类型',
    java_type      varchar(500)              null comment 'JAVA类型',
    java_field     varchar(200)              null comment 'JAVA字段名',
    is_pk          char                      null comment '是否主键（1是）',
    is_increment   char                      null comment '是否自增（1是）',
    is_required    char                      null comment '是否必填（1是）',
    is_insert      char                      null comment '是否为插入字段（1是）',
    is_edit        char                      null comment '是否编辑字段（1是）',
    is_list        char                      null comment '是否列表字段（1是）',
    is_query       char                      null comment '是否查询字段（1是）',
    query_type     varchar(200) default 'EQ' null comment '查询方式（等于、不等于、大于、小于、范围）',
    html_type      varchar(200)              null comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    dict_type      varchar(200) default ''   null comment '字典类型',
    sort           int                       null comment '排序',
    create_by      varchar(64)  default ''   null comment '创建者',
    create_time    datetime                  null comment '创建时间',
    update_by      varchar(64)  default ''   null comment '更新者',
    update_time    datetime                  null comment '更新时间'
)
    comment '代码生成业务表字段' charset = utf8;


INSERT INTO `gen_table_column` VALUES (143, '21', 'menu_id', '菜单ID', 'bigint(20)', 'Long', 'menuId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (144, '21', 'menu_name', '菜单名称', 'varchar(50)', 'String', 'menuName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (145, '21', 'parent_id', '父菜单ID', 'bigint(20)', 'Long', 'parentId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (146, '21', 'order_num', '显示顺序', 'int(4)', 'Integer', 'orderNum', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (147, '21', 'path', '路由地址', 'varchar(200)', 'String', 'path', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (148, '21', 'component', '组件路径', 'varchar(255)', 'String', 'component', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (149, '21', 'is_frame', '是否为外链（0是 1否）', 'int(1)', 'Integer', 'isFrame', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (150, '21', 'is_cache', '是否缓存（0缓存 1不缓存）', 'int(1)', 'Integer', 'isCache', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (151, '21', 'menu_type', '菜单类型（M目录 C菜单 F按钮）', 'char(1)', 'String', 'menuType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 9, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (152, '21', 'visible', '菜单状态（0显示 1隐藏）', 'char(1)', 'String', 'visible', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (153, '21', 'status', '菜单状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 11, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (154, '21', 'perms', '权限标识', 'varchar(100)', 'String', 'perms', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (155, '21', 'icon', '菜单图标', 'varchar(100)', 'String', 'icon', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (156, '21', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 14, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (157, '21', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 15, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (158, '21', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 16, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (159, '21', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 17, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (160, '21', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 18, 'tony', '2021-08-12 15:49:51', '', NULL);

INSERT INTO `gen_table_column` VALUES (215, '28', 'user_id', '用户ID', 'bigint(20)', 'Long', 'userId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (216, '28', 'dept_id', '部门ID', 'bigint(20)', 'Long', 'deptId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (217, '28', 'user_name', '用户账号', 'varchar(30)', 'String', 'userName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (218, '28', 'nick_name', '用户昵称', 'varchar(30)', 'String', 'nickName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 4, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (219, '28', 'user_type', '用户类型（00系统用户）', 'varchar(2)', 'String', 'userType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 5, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (220, '28', 'email', '用户邮箱', 'varchar(50)', 'String', 'email', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (221, '28', 'phonenumber', '手机号码', 'varchar(11)', 'String', 'phonenumber', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (222, '28', 'sex', '用户性别（0男 1女 2未知）', 'char(1)', 'String', 'sex', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 8, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (223, '28', 'avatar', '头像地址', 'varchar(100)', 'String', 'avatar', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (224, '28', 'password', '密码', 'varchar(100)', 'String', 'password', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (225, '28', 'status', '帐号状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 11, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (226, '28', 'del_flag', '删除标志（0代表存在 2代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 12, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (227, '28', 'login_ip', '最后登录IP', 'varchar(128)', 'String', 'loginIp', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (228, '28', 'login_date', '最后登录时间', 'datetime', 'Date', 'loginDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 14, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (229, '28', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 15, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (230, '28', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 16, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (231, '28', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 17, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (232, '28', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 18, 'tony', '2021-08-12 15:49:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (233, '28', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 19, 'tony', '2021-08-12 15:49:51', '', NULL);

-- auto-generated definition
create table sys_user
(
    id              int auto_increment comment '用户ID'
        primary key,
    account         varchar(50)                           not null comment '用户账号',
    password        varchar(100)                          not null comment '密码',
    nick_name       varchar(30)                           not null comment '用户昵称',
    user_type       varchar(2)  default '00'              null comment '用户类型（00系统用户）',
    email           varchar(50) default ''                null comment '用户邮箱',
    avatar          varchar(100)                          null comment '头像地址',
    sex             tinyint     default 3                 not null comment '性别(字典 1男 2女 3未知)',
    phone           varchar(50)                           null comment '手机号码',
    last_login_time datetime                              null comment '最后一次登录时间',
    last_login_ip   varchar(100)                          null comment '最后一次登录IP',
    status          tinyint     default 0                 not null comment '状态（字典 0正常 1冻结 2删除）',
    create_by       varchar(64)                           null comment '创建者',
    create_time     datetime                              null comment '创建时间',
    update_by       varchar(64)                           null comment '更新者',
    update_time     timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    remark          varchar(500)                          null comment '备注'
)
    comment '用户信息表';

