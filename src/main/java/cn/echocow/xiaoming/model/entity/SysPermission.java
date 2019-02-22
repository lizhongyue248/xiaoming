package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 权限
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {
    /**
     * 组件名称
     */
    @NotNull
    @TableField(value = "component")
    private String component;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 菜单切换时是否保活
     */
    @TableField(value = "keep_alive")
    private Boolean keepAlive;

    /**
     * 请求路径规则
     */
    @NotNull
    @TableField(value = "match_url")
    private String matchUrl;

    /**
     * 请求方法 GET、POST、PUT、PATCH、DELETE、ALL
     */
    @NotNull
    @TableField(value = "method")
    private String method = "ALL";

    /**
     * 菜单名称
     */
    @NotNull
    @TableField(value = "name")
    private String name;

    /**
     * 路由 path
     */
    @NotNull
    @TableField(value = "request_path")
    private String requestPath;

    /**
     * 父菜单 Id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    public static final String COL_COMPONENT = "component";

    public static final String COL_ICON = "icon";

    public static final String COL_KEEP_ALIVE = "keep_alive";

    public static final String COL_MATCH_URL = "match_url";

    public static final String COL_METHOD = "method";

    public static final String COL_NAME = "name";

    public static final String COL_REQUEST_PATH = "request_path";

    public static final String COL_PARENT_ID = "parent_id";
}