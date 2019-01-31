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
 * 作业表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 15:52
 */
@Data
@Entity
@Table(name = "homework")
@EntityListeners(AuditingEntityListener.class)
public class Homework implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
