package cn.echocow.xiaoming.model.view;

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
@Subselect("select `u`.`id`        AS `id`," +
        "       `u`.`username`     AS `username`," +
        "       `u`.`nickname`     AS `nickname`," +
        "       `r`.`name`         AS `role_name`," +
        "       `r`.`name_zh`      AS `role_name_zh`," +
        "       `p`.`name`         AS `menu_name`," +
        "       `p`.`request_path` AS `request_path`," +
        "       `p`.`match_url`    AS `match_url`," +
        "       `u`.`email`        AS `email`," +
        "       `u`.`img`          AS `img`," +
        "       `u`.`phone`        AS `phone`," +
        "       `u`.`enabled`      AS `enabled`," +
        "       `u`.`remark`       AS `remark`" +
        "from ((((`gakDev`.`sys_user` `u` join `gakDev`.`sys_user_role` `ur`) join `gakDev`.`sys_role` `r`) join `gakDev`.`sys_permission_role` `pr`)" +
        "       join `gakDev`.`sys_permission` `p`)" +
        "where ((`u`.`id` = `ur`.`user_id`) and (`ur`.`role_id` = `r`.`id`) and (`r`.`id` = `pr`.`role_id`) and" +
        "       (`pr`.`permission_id` = `pr`.`id`) and (`u`.`enabled` = TRUE))")
@Synchronize({"sys_user", "sys_role", "sys_menu", "sys_user_role", "sys_menu_role"})
public class SysUserRolePermission {
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
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 路由 path
     */
    @Column(name = "request_path")
    private String requestPath;

    /**
     * 请求路径规则
     */
    @Column(name = "match_url")
    private String matchUrl;

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
