package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.base.BaseEntity;
import cn.echocow.xiaoming.controller.*;
import lombok.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

/**
 * rest 风格资源封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:32
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestResource<T extends BaseEntity> extends ResourceSupport {
    private T entity;
    private final static String SYS = "Sys";
    private Object others;

    public RestResource(T entity, Class<?> clazz) {
        this.entity = entity;
        String name = clazz.getName();
        // 手动封装方便点
        add(new Link(ControllerLinkBuilder.linkTo(clazz).toString() + "/" + entity.getId()).withSelfRel());
        if (name.contains(SYS)) {
            add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
            add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
            add(ControllerLinkBuilder.linkTo(SysLogController.class).withRel("sys_log"));
            add(ControllerLinkBuilder.linkTo(SysRoleController.class).withRel("sys_role"));
        } else {
            add(ControllerLinkBuilder.linkTo(ClassroomController.class).withRel("classroom"));
            add(ControllerLinkBuilder.linkTo(FileController.class).withRel("file"));
            add(ControllerLinkBuilder.linkTo(HomeworkController.class).withRel("homework"));
            add(ControllerLinkBuilder.linkTo(StudentController.class).withRel("student"));
            add(ControllerLinkBuilder.linkTo(TaskController.class).withRel("task"));
        }
    }
}
