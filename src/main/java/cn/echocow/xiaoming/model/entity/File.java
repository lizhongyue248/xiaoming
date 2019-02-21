package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 17:39
 */
@Data
@TableName(value = "file")
@EqualsAndHashCode(callSuper = true)
public class File extends BaseEntity {
    /**
     * 文件名称，重命名后的文件名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件名称，重命名前的文件名称
     */
    @TableField(value = "old_name")
    private String oldName;

    /**
     * 文件类型
     */
    @TableField(value = "type")
    private String type;

    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 文件大小
     */
    @TableField(value = "size")
    private String size;

    /**
     * 保存文件的路径名称
     */
    @TableField(value = "dir_name")
    private String dirName;

    @TableField(value = "student_id")
    private Long studentId;

    public static final String COL_NAME = "name";

    public static final String COL_OLD_NAME = "old_name";

    public static final String COL_TYPE = "type";

    public static final String COL_TASK_ID = "task_id";

    public static final String COL_SIZE = "size";

    public static final String COL_DIR_NAME = "dir_name";
}