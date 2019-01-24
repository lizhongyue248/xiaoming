package cn.echocow.xiaoming.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 15:24
 */
@Data
@Entity
@Table(name = "sys_user_role")
@EntityListeners(AuditingEntityListener.class)
public class SysUserRole implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false, columnDefinition = "bit not null default 1 comment '是否启用'")
    private Boolean enabled;

    /**
     * 创建时间
     */
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
    @Column(name = "remark")
    private String remark;
}