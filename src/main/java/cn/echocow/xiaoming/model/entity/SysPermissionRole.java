package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 菜单角色关联表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 15:24
 */
@Data
@Entity
@Table(name = "sys_permission_role")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionRole extends BaseEntity {

    /**
     * 菜单id
     */
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

}
