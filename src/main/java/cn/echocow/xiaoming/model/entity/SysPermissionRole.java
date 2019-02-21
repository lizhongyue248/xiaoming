package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限角色关联表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_permission_role")
public class SysPermissionRole extends BaseEntity {

    @TableField(value = "permission_id")
    private Long permissionId;

    @TableField(value = "role_id")
    private Long roleId;

    public static final String COL_PERMISSION_ID = "permission_id";

    public static final String COL_ROLE_ID = "role_id";
}