package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "task")
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {
    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 任务名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 开启时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    @TableField(value = "classroom_id")
    private Long classroomId;

    @TableField(value = "homework_id")
    private Long homeworkId;

    @TableField(exist = false)
    private transient Classroom classroom;

    @TableField(exist = false)
    private transient Homework homework;

    public static final String COL_END_TIME = "end_time";

    public static final String COL_NAME = "name";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_CLASSROOM_ID = "classroom_id";

    public static final String COL_HOMEWORK_ID = "homework_id";
}