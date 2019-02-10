package cn.echocow.xiaoming.model.entity;


import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

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

}
