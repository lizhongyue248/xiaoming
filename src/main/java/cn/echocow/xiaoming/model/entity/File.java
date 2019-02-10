package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 文件上传表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 16:06
 */
@Data
@Entity
@Table(name = "file")
@EqualsAndHashCode(callSuper = true)
public class File extends BaseEntity {

    /**
     * 文件名称，重命名后的文件名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '文件名称，重命名后的文件名称'")
    private String name;

    /**
     * 文件名称，重命名前的文件名称
     */
    @Column(name = "old_name", columnDefinition = "varchar(255) not null comment '文件名称，重命名前的文件名称'")
    private String oldName;

    /**
     * 文件大小
     */
    @Column(name = "size", columnDefinition = "varchar(255) not null comment '文件大小'")
    private String size;

    /**
     * 文件类型
     */
    @Column(name = "type", columnDefinition = "varchar(100) not null comment '文件类型'")
    private String type;

    /**
     * 文件对应的作业任务
     */
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
