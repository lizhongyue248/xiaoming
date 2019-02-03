package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.controller.SysPermissionController;
import cn.echocow.xiaoming.controller.SysRoleController;
import cn.echocow.xiaoming.controller.SysUserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 00:10
 */
public class ApplicationResource extends ResourceSupport {
    private final static String PAGE = "{?page=&size=}";
    public ApplicationResource() {
        add(new Link(ControllerLinkBuilder.linkTo(SysPermissionController.class).toString() + PAGE).withRel("sys_menu"));
        add(new Link(ControllerLinkBuilder.linkTo(SysUserController.class).toString() + PAGE).withRel("sys_user"));
        add(new Link(ControllerLinkBuilder.linkTo(SysRoleController.class).toString() + PAGE).withRel("sys_role"));
    }
}
