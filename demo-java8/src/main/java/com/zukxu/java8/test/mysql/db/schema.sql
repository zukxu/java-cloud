CREATE TABLE `stock`
(
    `id`      int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`    varchar(50)      NOT NULL DEFAULT '' COMMENT '名称',
    `count`   int(11)          NOT NULL COMMENT '库存',
    `sale`    int(11)          NOT NULL COMMENT '已售',
    `version` int(11)          NOT NULL COMMENT '乐观锁，版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `stock_order`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `sid`         int(11)          NOT NULL COMMENT '库存ID',
    `name`        varchar(30)      NOT NULL DEFAULT '' COMMENT '商品名称',
    `create_time` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8

INSERT INTO `little-tools`.stock (id, name, count, sale, version) VALUES (1, 'apple', 10, 0, 1);