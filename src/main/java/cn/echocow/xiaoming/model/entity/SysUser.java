package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 15:24
 */
@Data
@Entity
@Table(name = "sys_user")
@ToString(exclude = "roles")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity implements UserDetails {

    /**
     * 用户昵称
     */
    @Length(max = 20)
    @Column(name = "nickname", columnDefinition = "varchar(20) not null comment '用户昵称'")
    private String nickname;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @Length(max = 50, message = "用户名长度最大为50")
    @Column(name = "username", unique = true, columnDefinition = "varchar(50) not null comment '用户名'")
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Column(name = "password", columnDefinition = "varchar(255) not null comment '密码'")
    private String password;

    /**
     * 电话号码
     */
    @Length(min = 11, max = 11, message = "手机长度只能为11位")
    @Column(name = "phone", unique = true, columnDefinition = "varchar(255) null comment '电话号码'")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱地址不合法")
    @Column(name = "email", unique = true, columnDefinition = "varchar(255) null comment '邮箱'")
    private String email;

    /**
     * 头像
     */
    @Column(name = "img", columnDefinition = "varchar(255) null comment '头像'")
    private String img;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false, columnDefinition = "bit not null default 1 comment '是否启用'")
    private Boolean enabled = true;

    /**
     * 当前用户的权限
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "users")
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    private List<SysRole> roles;

    /**
     * 授权
     *
     * @return authorities
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(sysRole -> authorities.add(new SimpleGrantedAuthority(sysRole.getName())));
        return authorities;
    }

    /**
     * 帐户是否过期
     *
     * @return 未过期
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否锁定
     *
     * @return 未锁定
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账号密码是否过期
     *
     * @return 未过期
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否启用
     *
     * @return 是否启用
     */
    @Override
    public boolean isEnabled() {
        return getEnabled();
    }
}
