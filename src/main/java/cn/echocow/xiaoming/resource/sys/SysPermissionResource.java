package cn.echocow.xiaoming.resource.sys;

import cn.echocow.xiaoming.controller.sys.SysLogController;
import cn.echocow.xiaoming.controller.sys.SysPermissionController;
import cn.echocow.xiaoming.controller.sys.SysUserController;
import cn.echocow.xiaoming.entity.sys.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 00:15
 */
@Getter
@Setter
public class SysPermissionResource extends ResourceSupport{
    private SysUser sysUser;

    public SysPermissionResource(SysUser sysUser) {
        this.sysUser = sysUser;
        Long id = sysUser.getId();
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SysUserController.class).sysUser(id)).withSelfRel());
        add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
        add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
        add(ControllerLinkBuilder.linkTo(SysLogController.class).withRel("sys_log"));
    }
}
