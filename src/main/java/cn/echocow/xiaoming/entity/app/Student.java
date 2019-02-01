package cn.echocow.xiaoming.entity.app;

import cn.echocow.xiaoming.entity.sys.SysUser;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
public class Student implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
