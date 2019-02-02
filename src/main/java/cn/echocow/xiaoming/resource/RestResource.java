package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.base.BaseEntity;
import cn.echocow.xiaoming.controller.app.*;
import cn.echocow.xiaoming.controller.sys.SysLogController;
import cn.echocow.xiaoming.controller.sys.SysPermissionController;
import cn.echocow.xiaoming.controller.sys.SysRoleController;
import cn.echocow.xiaoming.controller.sys.SysUserController;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * rest 风格资源封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:32
 */
@Getter
@Setter
public class RestResource<T extends BaseEntity> extends ResourceSupport {
    private T entity;
    private final static String SYS = "Sys";

    public RestResource(T entity, Class<?> clazz) {
        this.entity = entity;
        Long id = entity.getId();
        String name = clazz.getName();
        String methodName = clazz.getAnnotation(RequestMapping.class).value()[0];
        methodName = StringUtils.left(methodName, methodName.length() - 1);
        methodName = StringUtils.right(methodName, methodName.length() - 1);
        Method method;
        try {
            method = clazz.getMethod(methodName, Long.class);
            add(ControllerLinkBuilder.linkTo(method, id).withSelfRel());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
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
}
