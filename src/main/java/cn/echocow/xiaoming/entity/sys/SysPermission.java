package cn.echocow.xiaoming.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 21:31
 */
@Data
@Entity
@Table(name = "sys_permission")
@EntityListeners(AuditingEntityListener.class)
public class SysPermission implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '菜单名称'")
    private String name;

    /**
     * 请求路径规则
     */
    @Column(name = "match_url", columnDefinition = "varchar(255) not null comment '请求路径规则'")
    private String matchUrl;

    /**
     * 路由 path
     */
    @Column(name = "request_path", columnDefinition = "varchar(255) not null comment '路由 path'")
    private String requestPath;

    /**
     * 请求方法 GET、POST、PUT、PATCH、DELETE、ALL
     */
    @Column(name = "method", columnDefinition = "varchar(50) not null comment '请求方法 GET、POST、PUT、PATCH、DELETE、ALL'")
    private String method;

    /**
     * 组件名称
     */
    @Column(name = "component", columnDefinition = "varchar(255) not null comment '组件名称'")
    private String component;

    /**
     * 菜单图标
     */
    @Column(name = "icon", columnDefinition = "varchar(255) null comment '菜单图标'")
    private String icon;

    /**
     * 菜单切换时是否保活
     */
    @Column(name = "keep_alive", columnDefinition = "bit not null default 1 comment '菜单切换时是否保活'")
    private Boolean keepAlive;

    /**
     * 父菜单id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private SysPermission sysPermission;

    /**
     * 排序
     */
    @OrderColumn(name = "sort")
    private Integer sort;

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
    @Column(name = "create_user")
    private String createUser;

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
    @Column(name = "modify_user")
    private String modifyUser;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 菜单角色
     * 双向映射造成数据重复查询死循环问题
     */
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<SysRole> roles = new ArrayList<>();
}
