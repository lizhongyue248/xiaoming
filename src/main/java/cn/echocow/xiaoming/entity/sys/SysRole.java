package cn.echocow.xiaoming.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.ArrayList;
import java.util.List;

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
@ToString(exclude = {"users", "permissions"})
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
    @Column(name = "name", unique = true, nullable = false, columnDefinition = "varchar(20) not null comment '角色名,按照SpringSecurity的规范,以ROLE_开头'")
    private String name;

    /**
     * 角色名,中文
     */
    @Column(name = "name_zh", nullable = false, columnDefinition = "varchar(255) not null comment '角色名,中文'")
    private String nameZh;

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
    private Long createUser;

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
    private Long modifyUser;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


    /**
     * 当前角色的菜单
     */
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "sys_permission_role", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<SysPermission> permissions = new ArrayList<>();

    /**
     * 当前角色对应的用户
     * 双向映射造成数据重复查询死循环问题
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<SysUser> users = new ArrayList<>();

}
