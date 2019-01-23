package cn.echocow.xiaoming.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 菜单表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 21:31
 */
@Data
@Entity
@ToString(exclude = "roles")
@Table(name = "sys_menu")
@EntityListeners(AuditingEntityListener.class)
public class SysMenu implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    @Basic
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '菜单名称'")
    private String name;

    /**
     * 请求路径规则
     */
    @Basic
    @Column(name = "url", columnDefinition = "varchar(255) not null comment '请求路径规则'")
    private String url;

    /**
     * 路由 path
     */
    @Basic
    @Column(name = "path", columnDefinition = "varchar(255) not null comment '路由 path'")
    private String path;

    /**
     * 组件名称
     */
    @Basic
    @Column(name = "component", columnDefinition = "varchar(255) not null comment '组件名称'")
    private String component;

    /**
     * 菜单图标
     */
    @Basic
    @Column(name = "icon", columnDefinition = "varchar(255) null comment '菜单图标'")
    private String icon;

    /**
     * 菜单切换时是否保活
     */
    @Basic
    @Column(name = "keep_alive", columnDefinition = "bit not null default 1 comment '菜单切换时是否保活'")
    private Boolean keepAlive;

    /**
     * 是否登陆后才能访问
     */
    @Basic
    @Column(name = "require_auth", columnDefinition = "bit not null default 1 comment '是否登陆后才能访问'")
    private Boolean requireAuth;

    /**
     * 父菜单id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private SysMenu sysMenu;

    /**
     * 排序
     */
    @Basic
    @OrderColumn(name = "sort")
    private Integer sort;

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

//    /**
//     * 菜单角色
//     * 双向映射造成数据重复查询死循环问题
//     */
//    @ManyToMany(mappedBy = "menus")
//    private Set<SysRole> roles;
}
