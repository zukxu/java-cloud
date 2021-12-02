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

