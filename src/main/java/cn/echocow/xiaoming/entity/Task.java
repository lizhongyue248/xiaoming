package cn.echocow.xiaoming.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Task extends BaseEntity {

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
}
