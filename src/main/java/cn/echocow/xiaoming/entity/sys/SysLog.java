package cn.echocow.xiaoming.entity.sys;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 日志表
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 16:13
 */
@Data
@Entity
@Table(name = "sys_log")
@EntityListeners(AuditingEntityListener.class)
public class SysLog {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 浏览器名称
     */
    @Column(name = "browser", columnDefinition = "varchar(255) null comment '浏览器名称'")
    private String browser;

    /**
     * 操作系统
     */
    @Column(name = "os", columnDefinition = "varchar(255) null comment '操作系统'")
    private String os;

    /**
     * IP 地址
     */
    @Column(name = "ip", columnDefinition = "varchar(255) null comment 'IP 地址'")
    private String ip;

    /**
     * 访问地址
     */
    @Column(name = "url", columnDefinition = "varchar(255) null comment '访问地址'")
    private String url;

    /**
     * 日志等级
     * 0. TRACE
     * 1. DEBUG
     * 2. INFO
     * 3. WARN
     * 4. ERROR
     * 5. FATAL
     */
    @Column(name = "level", columnDefinition = "varchar(4) null comment '日志等级 0. TRACE 1. DEBUG 2. INFO 3. WARN 4. ERROR 5. FATAL'")
    private Integer level;

    /**
     * 调用方法
     */
    @Column(name = "class_method", columnDefinition = "varchar(255) null comment '调用方法'")
    private String method;

    /**
     * 请求方式
     */
    @Column(name = "request_method", columnDefinition = "varchar(255) null comment '请求方式'")
    private String requestMethod;

    /**
     * 设备类型
     */
    @Column(name = "device_type", columnDefinition = "varchar(255) null comment '设备类型'")
    private String deviceType;

    /**
     * 请求参数
     */
    @Column(name = "args", columnDefinition = "varchar(2048) null comment '请求参数'")
    private String args;

    /**
     * 返回结果
     */
    @Column(name = "result", columnDefinition = "varchar(2048) null comment '返回结果'")
    private String result;

    /**
     * 排序
     */
    @OrderColumn(name = "sort")
    private Integer sort;

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
