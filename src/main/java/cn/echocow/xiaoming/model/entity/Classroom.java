package cn.echocow.xiaoming.model.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 班级，为了区别关键字 class, 使用复数
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 15:32
 */
@Data
@Entity
@Table(name = "classroom")
@EqualsAndHashCode(callSuper = true)
public class Classroom extends BaseEntity {

    /**
     * 班级名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null comment '班级名称'")
    private String name;

    /**
     * 班级人数
     */
    @Column(name = "number", columnDefinition = "int(10) not null comment '班级人数'")
    private Integer number;
}
