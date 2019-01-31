package cn.echocow.xiaoming.entity.app;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 作业任务
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 16:01
 */
@Data
@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '任务名称'")
    private String name;

    /**
     * 作业
     */
    @OneToOne
    @JoinColumn(name = "homework_id")
    private Homework homework;

    /**
     * 班级
     */
    @OneToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    /**
     * 开启时间
     */
    @Column(name = "start_time", nullable = false, columnDefinition = "datetime not null default now() comment '开启时间'")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time", nullable = false, columnDefinition = "datetime not null default now() comment '结束时间'")
    private LocalDateTime endTime;

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
    private Long createUser;

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
    private Long modifyUser;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}
