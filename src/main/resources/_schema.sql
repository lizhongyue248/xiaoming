create table if not exists sys_user
(
  id          bigint auto_increment primary key,
  nickname    varchar(255)                       not null comment '用户昵称',
  username    varchar(255)                       not null comment '用户名',
  password    varchar(255)                       not null comment '密码',
  phone       varchar(255)                       null comment '电话号码',
  email       varchar(255)                       null comment '邮箱',
  img         varchar(255)                       null comment '头像',
  sex         int      default 1                 not null comment '性别,1男0女',
  create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  create_user bigint                             null,
  modify_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
  modify_user bigint                             null,
  remark      varchar(255)                       null,
  enabled     bit      default b'1'              not null comment '是否启用',
  constraint UK_51bvuyvihefoh4kp5syh2jpi4
    unique (username),
  constraint UK_ahtq5ew3v0kt1n7hf1sgp7p8l
    unique (email),
  constraint UK_pulp17fvich5aby4m0kc820h6
    unique (phone),
  constraint FK85hu97kwj1ky8epnld3fhcgcd
    foreign key (modify_user) references sys_user (id),
  constraint FKij1watx0thkhbwejfd7n45lu7
    foreign key (create_user) references sys_user (id)
) comment '用户表' ENGINE = InnoDB
                DEFAULT CHARSET = utf8mb4;

create table if not exists sys_menu
(
  id           bigint auto_increment primary key,
  name         varchar(255)                       not null comment '菜单名称',
  url          varchar(255)                       not null comment '请求路径规则',
  path         varchar(255)                       not null comment '路由 path',
  component    varchar(255)                       not null comment '组件名称',
  icon         varchar(255)                       null comment '菜单图标',
  parent_id    bigint                             null,
  keep_alive   bit      default b'1'              not null comment '菜单切换时是否保活',
  require_auth bit      default b'1'              not null comment '是否登陆后才能访问',
  create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  create_user  bigint                             null,
  modify_time  datetime default CURRENT_TIMESTAMP not null comment '修改时间',
  modify_user  bigint                             null,
  remark       varchar(255)                       null,
  enabled      bit      default b'1'              not null comment '是否启用',
  sort         int                                null,
  constraint FK2jrf4gb0gjqi8882gxytpxnhe
    foreign key (parent_id) references sys_menu (id),
  constraint FKpurs7a6mj5rqiwjv5yxdy2raa
    foreign key (create_user) references sys_user (id),
  constraint FKs0vuj3ixd5vywk6ml8qlfjmio
    foreign key (modify_user) references sys_user (id)
) comment '菜单表' ENGINE = InnoDB
                DEFAULT CHARSET = utf8mb4;

create table if not exists sys_role
(
  id          bigint auto_increment primary key,
  name        varchar(20)                        not null comment '角色名,按照SpringSecurity的规范,以ROLE_开头',
  name_zh     varchar(255)                       not null comment '角色名,中文',
  create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  create_user bigint                             null,
  modify_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
  modify_user bigint                             null,
  enabled     bit      default b'1'              not null comment '是否启用',
  sort        int                                null,
  remark      varchar(255)                       null,
  constraint UK_bqy406dtsr7j7d6fawi1ckyn1
    unique (name),
  constraint FK4tw2t15qugducqc0c85yru4hb
    foreign key (modify_user) references sys_user (id),
  constraint FKc4i6wad8pcwhx2u9dkrmvhw0u
    foreign key (create_user) references sys_user (id)
) comment '角色表' ENGINE = InnoDB
                DEFAULT CHARSET = utf8mb4;

create table if not exists sys_menu_role
(
  id          bigint auto_increment primary key,
  menu_id     bigint                             not null,
  role_id     bigint                             not null,
  create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  create_user bigint                             null,
  modify_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
  modify_user bigint                             null,
  enabled     bit      default b'1'              not null comment '是否启用',
  remark      varchar(255)                       null,
  constraint FK4mqc2xud9h664fq2fjlh2x70o
    foreign key (create_user) references sys_user (id),
  constraint FK9m446slsbm3gxyvk1g8exghxq
    foreign key (menu_id) references sys_menu (id),
  constraint FKfbncvokt5wbp70csygfob3mdd
    foreign key (modify_user) references sys_user (id),
  constraint FKskkvj453r18wqido8gv8tlnbp
    foreign key (role_id) references sys_role (id)
) comment '菜单角色表' ENGINE = InnoDB
                  DEFAULT CHARSET = utf8mb4;

create table if not exists sys_user_role
(
  id          bigint auto_increment primary key,
  role_id     bigint                             not null,
  user_id     bigint                             not null,
  create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  create_user bigint                             null,
  modify_user bigint                             null,
  modify_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
  enabled     bit      default b'1'              not null comment '是否启用',
  remark      varchar(255)                       null,
  constraint FK1rgrkjx2kludopqd548oqn2er
    foreign key (modify_user) references sys_user (id),
  constraint FKb40xxfch70f5qnyfw8yme1n1s
    foreign key (user_id) references sys_user (id),
  constraint FKhh52n8vd4ny9ff4x9fb8v65qx
    foreign key (role_id) references sys_role (id),
  constraint FKk0at77sas8p2iil0hkmvhaimh
    foreign key (create_user) references sys_user (id)
) comment '用户角色表' ENGINE = InnoDB
                  DEFAULT CHARSET = utf8mb4;

create or replace view sys_user_role_menu as
select `u`.`id`       AS `id`,
       `u`.`username` AS `username`,
       `u`.`nickname` AS `nickname`,
       `r`.`name`     AS `role_name`,
       `r`.`name_zh`  AS `role_name_zh`,
       `m`.`name`     AS `menu_name`,
       `m`.`path`     AS `path`,
       `m`.`url`      AS `url`,
       `u`.`email`    AS `email`,
       `u`.`img`      AS `img`,
       `u`.`phone`    AS `phone`,
       `u`.`sex`      AS `sex`,
       `u`.`enabled`  AS `enabled`,
       `u`.`remark`   AS `remark`
from ((((`gakDev`.`sys_user` `u` join `gakDev`.`sys_user_role` `ur`) join `gakDev`.`sys_role` `r`) join `gakDev`.`sys_menu_role` `mr`)
       join `gakDev`.`sys_menu` `m`)
where ((`u`.`id` = `ur`.`user_id`) and (`ur`.`role_id` = `r`.`id`) and (`r`.`id` = `mr`.`role_id`) and
       (`mr`.`menu_id` = `m`.`id`) and (`u`.`enabled` = TRUE) and (`ur`.`enabled` = TRUE) and (`r`.`enabled` = TRUE) and
       (`mr`.`enabled` = TRUE) and (`m`.`enabled` = TRUE));

