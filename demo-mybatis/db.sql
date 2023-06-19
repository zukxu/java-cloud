create table sys_user
(
    id           varchar(50)                               not null comment '用户ID'
        primary key,
    dept_id      varchar(50) default ''                    not null comment '部门id',
    login_name   varchar(50) default ''                    not null comment '登录用户',
    user_name    varchar(50) default ''                    not null comment '用户账号',
    password     varchar(50) default ''                    not null comment '密码',
    sex          varchar(50) default ''                    not null comment '性别(字典 1男 2女 3未知)',
    email        varchar(50) default ''                    not null comment '用户邮箱',
    phone        varchar(50) default ''                    not null comment '手机号码',
    status       varchar(50) default '0'                    not null comment '状态（字典 0正常 1冻结）',
    del_flag     varchar(50) default '0'                    not null comment '逻辑删除(0存在 1删除)',
    create_by    varchar(50) default ''                    not null comment '创建者',
    create_time  datetime    default '1000-01-01 00:00:00' not null comment '创建时间',
    update_by    varchar(50) default ''                    not null comment '更新者',
    update_time  timestamp   default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '更新时间',
    remark       varchar(50) default ''                    not null comment '备注',
    level        varchar(50) default ''                    not null comment '用户级别 1、省 2、市 3、区县、4、网格',
    yd4a_account varchar(50) null comment '移动4A账号',
    oa_account   varchar(50) null comment '省OA账号',
    crm_account  varchar(50) null comment '多终端账号'
)
    comment 'sys_user';