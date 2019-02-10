package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * 学生表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 15:30
 */
@Data
@Entity
@Table(name = "student")
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {

    /**
     * 学生名称
     */
    @Column(name = "name", columnDefinition = "varchar(20) not null comment '学生名称'")
    private String name;

    /**
     * 学号
     */
    @Column(name = "no", columnDefinition = "varchar(100) not null comment '学号'")
    private String no;

    /**
     * 性别,1男0女
     */
    @NotNull
    @Pattern(regexp = "[01]", message = "性别只能为男或女")
    @Column(name = "sex", columnDefinition = "int not null default 1 comment '性别,1男0女'")
    private Integer sex;

    /**
     * 一对一，对应当前学生所在的班级
     */
    @OneToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    /**
     * 一对一，对应当前当前学生的用户
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private SysUser user;



}
