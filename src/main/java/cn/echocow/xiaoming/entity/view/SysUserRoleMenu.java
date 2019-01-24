package cn.echocow.xiaoming.entity.view;

import org.springframework.data.annotation.Immutable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "sys_user_role_menu")
public class SysUserRoleMenu {
    @Id
    @Column(name = "id")
    private Long id;

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
