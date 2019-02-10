package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "start_time", nullable = false, columnDefinition = "datetime not null default now() comment '开启时间'")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "end_time", nullable = false, columnDefinition = "datetime not null default now() comment '结束时间'")
    private LocalDateTime endTime;
}