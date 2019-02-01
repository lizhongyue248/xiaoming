package cn.echocow.xiaoming.entity.app;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /**
     * 排序
     */
    @OrderColumn(name = "sort")
    private Integer sort;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false, columnDefinition = "bit not null default 1 comment '是否启用'")
    private Boolean enabled;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false, columnDefinition = "datetime not null default now() comment '创建时间'")
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    @CreatedBy
    @Column(name = "create_user")
    private String createUser;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time", nullable = false, columnDefinition = "datetime not null default now() comment '修改时间'")
    private LocalDateTime modifyTime;

    /**
     * 修改用户
     */
    @LastModifiedBy
    @Column(name = "modify_user")
    private String modifyUser;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}
