package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {
    /**
     * 角色名,按照SpringSecurity的规范,以ROLE_开头
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色名,中文
     */
    @TableField(value = "name_zh")
    private String nameZh;

    public static final String COL_NAME = "name";

    public static final String COL_NAME_ZH = "name_zh";
}