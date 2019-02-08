package cn.echocow.xiaoming.entity;

import cn.echocow.xiaoming.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 作业表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 15:52
 */
@Data
@Entity
@Table(name = "homework")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Homework extends BaseEntity {

    /**
     * 作业名称
     */
    @Column(name = "name", columnDefinition = "varchar(50) not null comment '作业名称'")
    private String name;

    /**
     * 作业详情
     */
    @Column(name = "detail", columnDefinition = "varchar(2048) null comment '作业详情'")
    private String detail;

    /**
     * 文件类型，用 | 进行分割
     */
    @Column(name = "type", columnDefinition = "varchar(100) null comment '文件类型，用 | 进行分割'")
    private String type;

    /**
     * 文件大小，不写默认 10MB
     */
    @Column(name = "size", columnDefinition = "varchar(256) null comment '文件大小，不写默认 10MB'")
    private String size = "10MB";

}
