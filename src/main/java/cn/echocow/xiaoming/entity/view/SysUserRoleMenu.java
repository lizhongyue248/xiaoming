package cn.echocow.xiaoming.entity.view;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.annotation.Immutable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户角色菜单视图 —— 只读
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-24 19:57
 */
@Data
@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Subselect("select `u`.`id`       AS `id`," +
        "       `u`.`username` AS `username`," +
        "       `u`.`nickname` AS `nickname`," +
        "       `r`.`name`     AS `role_name`," +
        "       `r`.`name_zh`  AS `role_name_zh`," +
        "       `m`.`name`     AS `menu_name`," +
        "       `m`.`path`     AS `path`," +
        "       `m`.`url`      AS `url`," +
        "       `u`.`email`    AS `email`," +
        "       `u`.`img`      AS `img`," +
        "       `u`.`phone`    AS `phone`," +
        "       `u`.`sex`      AS `sex`," +
        "       `u`.`enabled`  AS `enabled`," +
        "       `u`.`remark`   AS `remark`" +
        "from ((((`sys_user` `u` join `sys_user_role` `ur`) join `sys_role` `r`) join `sys_menu_role` `mr`)" +
        "       join `sys_menu` `m`)" +
        "where ((`u`.`id` = `ur`.`user_id`) and (`ur`.`role_id` = `r`.`id`) and (`r`.`id` = `mr`.`role_id`) and" +
        "       (`mr`.`menu_id` = `m`.`id`) and (`u`.`enabled` = TRUE) and (`ur`.`enabled` = TRUE) and (`r`.`enabled` = TRUE) and" +
        "       (`mr`.`enabled` = TRUE) and (`m`.`enabled` = TRUE))")
@Synchronize({"sys_user", "sys_role", "sys_menu", "sys_user_role", "sys_menu_role"})
public class SysUserRoleMenu {
    @Id
    @Column(name = "id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 角色名,按照SpringSecurity的规范,以ROLE_开头
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色名,中文
     */
    @Column(name = "role_name_zh")
    private String roleNameZh;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 路由 path
     */
    @Column(name = "path")
    private String path;

    /**
     * 请求路径规则
     */
    @Column(name = "url")
    private String url;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 头像
     */
    @Column(name = "img")
    private String img;

    /**
     * 电话号码
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 性别,1男0女
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
