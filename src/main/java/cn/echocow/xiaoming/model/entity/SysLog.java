package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {

    /**
     * 请求参数
     */
    @TableField(value = "args")
    private String args;

    /**
     * 浏览器名称
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 设备类型
     */
    @TableField(value = "device_type")
    private String deviceType;

    /**
     * IP 地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 日志等级 0. TRACE 1. DEBUG 2. INFO 3. WARN 4. ERROR 5. FATAL
     */
    @TableField(value = "level")
    private String level;

    /**
     * 调用方法
     */
    @TableField(value = "class_method")
    private String classMethod;

    /**
     * 操作系统
     */
    @TableField(value = "os")
    private String os;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 返回结果
     */
    @TableField(value = "result")
    private String result;

    /**
     * 访问地址
     */
    @TableField(value = "url")
    private String url;

    public static final String COL_ARGS = "args";

    public static final String COL_BROWSER = "browser";

    public static final String COL_DEVICE_TYPE = "device_type";

    public static final String COL_IP = "ip";

    public static final String COL_LEVEL = "level";

    public static final String COL_CLASS_METHOD = "class_method";

    public static final String COL_OS = "os";

    public static final String COL_REQUEST_METHOD = "request_method";

    public static final String COL_RESULT = "result";

    public static final String COL_URL = "url";
}