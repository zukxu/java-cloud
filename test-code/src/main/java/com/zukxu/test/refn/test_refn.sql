create table java_cloud.test_refn
(
    id    varchar(50)            not null comment 'id'
        primary key,
    title varchar(50) default '' not null comment '标题',
    p_id  varchar(50) null comment '父id'
) comment 'test_refn' charset = utf8mb4;

INSERT INTO java_cloud.test_refn (id, title, p_id)
VALUES ('1', 'A', null);
INSERT INTO java_cloud.test_refn (id, title, p_id)
VALUES ('2', 'AA', '1');
INSERT INTO java_cloud.test_refn (id, title, p_id)
VALUES ('3', 'AAA', '2');
INSERT INTO java_cloud.test_refn (id, title, p_id)
VALUES ('4', 'B', null);
INSERT INTO java_cloud.test_refn (id, title, p_id)
VALUES ('5', 'BB', '4');
