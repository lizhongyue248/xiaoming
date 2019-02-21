package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "homework")
@EqualsAndHashCode(callSuper = true)
public class Homework extends BaseEntity {
    /**
     * 作业详情
     */
    @TableField(value = "detail")
    private String detail;

    /**
     * 作业名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件类型，用 | 进行分割
     */
    @TableField(value = "type")
    private String type;

    /**
     * 文件大小，不写默认 10MB
     */
    @TableField(value = "size")
    private String size;

    public static final String COL_DETAIL = "detail";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_SIZE = "size";
}