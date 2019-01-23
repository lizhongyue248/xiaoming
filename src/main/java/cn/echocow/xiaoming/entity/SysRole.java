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
 * 角色表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 15:24
 */
@Data
@Entity
@Table(name = "sys_role")
@ToString(exclude = {"menus","users"})
@EntityListeners(AuditingEntityListener.class)
public class SysRole implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名,按照SpringSecurity的规范,以ROLE_开头
     */
    @Basic
    @Column(name = "name", unique = true, nullable = false, columnDefinition = "varchar(20) not null comment '角色名,按照SpringSecurity的规范,以ROLE_开头'")
    private String name;

    /**
     * 角色名,中文
     */
    @Basic
    @Column(name = "name_zh", nullable = false, columnDefinition = "varchar(255) not null comment '角色名,中文'")
    private String nameZh;

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

    /**
     * 当前角色的菜单
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_menu_role", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<SysMenu> menus;

//    /**
//     * 当前角色对应的用户
//     * 双向映射造成数据重复查询死循环问题
//     */
//    @ManyToMany(mappedBy = "roles")
//    private Set<SysUser> users;

}
