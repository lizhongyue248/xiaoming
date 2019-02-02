package cn.echocow.xiaoming.resource.sys;

import cn.echocow.xiaoming.controller.sys.SysPermissionController;
import cn.echocow.xiaoming.controller.sys.SysUserController;
import cn.echocow.xiaoming.entity.sys.SysLog;
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
public class SysLogResource extends ResourceSupport{
    private SysLog sysLog;

    public SysLogResource(){
    }

    public SysLogResource(SysLog sysLog) {
        this.sysLog = sysLog;
        Long id = sysLog.getId();
//        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SysLog.class).sysUser(id)).withSelfRel());
        add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
        add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
    }
}
