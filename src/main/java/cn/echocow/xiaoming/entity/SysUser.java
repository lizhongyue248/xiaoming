package cn.echocow.xiaoming.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 15:24
 */
@Data
@Entity
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
@Table(name = "sys_user")
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SysUser implements UserDetails {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户昵称
     */
    @Basic
    @NotNull
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '用户昵称'")
    private String name;

    /**
     * 用户名
     */
    @Basic
    @NotNull
    @Column(name = "username", unique = true, columnDefinition = "varchar(255) not null comment '用户名'")
    private String username;

    /**
     * 密码
     */
    @Basic
    @NotNull
    @Column(name = "password", columnDefinition = "varchar(255) not null comment '密码'")
    @JsonIgnore
    private String password;

    /**
     * 性别,1男0女
     */
    @Basic
    @NotNull
    @Pattern(regexp = "[01]", message = "性别只能为男或女")
    @Column(name = "sex", columnDefinition = "int not null default 1 comment '性别,1男0女'")
    private Integer sex;

    /**
     * 电话号码
     */
    @Basic
    @Length(min = 11, max = 11)
    @Column(name = "phone", unique = true, columnDefinition = "varchar(255) null comment '电话号码'")
    private String phone;

    /**
     * 邮箱
     */
    @Basic
    @Email
    @Column(name = "email", unique = true, columnDefinition = "varchar(255) null comment '邮箱'")
    private String email;

    /**
     * 头像
     */
    @Basic
    @Column(name = "img", columnDefinition = "varchar(255) null comment '头像'")
    private String img;

    /**
     * 是否启用
     */
    @Basic
    @Column(name = "enabled", nullable = false, columnDefinition = "bit not null default 1 comment '是否启用'")
    private Boolean enabled;

    /**
     * 创建时间
     */
    @Basic
    @CreatedDate
    @Column(name = "create_time", nullable = false, columnDefinition = "datetime not null default now() comment '创建时间'")
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    @CreatedBy
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "create_user")
    private SysUser createUser;

    /**
     * 修改时间
     */
    @Basic
    @LastModifiedDate
    @Column(name = "modify_time", nullable = false, columnDefinition = "datetime not null default now() comment '修改时间'")
    private LocalDateTime modifyTime;

    /**
     * 修改用户
     */
    @LastModifiedBy
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "modify_user")
    private SysUser modifyUser;

    /**
     * 备注
     */
    @Basic
    @Column(name = "remark")
    private String remark;

    /**
     * 当前用户的权限
     */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SysRole> roles;

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
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否锁定
     *
     * @return 未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账号密码是否过期
     *
     * @return 未过期
     */
    @Override
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
