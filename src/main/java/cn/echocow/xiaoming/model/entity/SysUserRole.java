package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "user_id")
    private Long userId;

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_MODIFY_TIME = "modify_time";

    public static final String COL_MODIFY_USER = "modify_user";

    public static final String COL_REMARK = "remark";

    public static final String COL_SORT = "sort";

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_USER_ID = "user_id";
}