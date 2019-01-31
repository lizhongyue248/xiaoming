package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.controller.sys.SysPermissionController;
import cn.echocow.xiaoming.controller.sys.SysRoleController;
import cn.echocow.xiaoming.controller.sys.SysUserController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 00:10
 */
public class ApplicationResource extends ResourceSupport {
    public ApplicationResource() {
        add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
        add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
        add(ControllerLinkBuilder.linkTo(SysRoleController.class).withRel("sys_role"));
    }
}
