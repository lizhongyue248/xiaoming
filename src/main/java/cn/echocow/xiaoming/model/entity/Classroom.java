package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 班级
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "classroom")
@EqualsAndHashCode(callSuper = true)
public class Classroom extends BaseEntity {
    /**
     * 班级名称
     */
    @NotNull
    @TableField(value = "name")
    private String name;

    /**
     * 班级人数
     */
    @NotNull
    @TableField(value = "number")
    private Integer number;

    public static final String COL_NAME = "name";

    public static final String COL_NUMBER = "number";
}