package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "student")
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {
    /**
     * 学生名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 学号
     */
    @TableField(value = "no")
    private String no;

    /**
     * 性别,1男0女
     */
    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "classroom_id")
    private Long classroomId;

    @TableField(value = "user_id")
    private Long userId;

    public static final String COL_NAME = "name";

    public static final String COL_NO = "no";

    public static final String COL_SEX = "sex";

    public static final String COL_CLASSROOM_ID = "classroom_id";

    public static final String COL_USER_ID = "user_id";
}