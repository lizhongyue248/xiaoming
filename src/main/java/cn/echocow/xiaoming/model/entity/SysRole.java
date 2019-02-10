package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
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
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"users", "permissions"})
public class SysRole extends BaseEntity {

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
     * 当前角色的菜单
     */
    @JsonIgnore
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
