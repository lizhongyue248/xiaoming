package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.controller.SysPermissionController;
import cn.echocow.xiaoming.controller.SysRoleController;
import cn.echocow.xiaoming.controller.SysUserController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import java.util.List;

/**
 * rest 风格集合资源封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 00:10
 */
@Getter
@Setter
public class RestResources<T> extends Resources<T> {
    private PageInfo page;
    private List<T> collect;

    public RestResources(List<T> collect) {
        this.collect = collect;
        add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
        add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
        add(ControllerLinkBuilder.linkTo(SysRoleController.class).withRel("sys_role"));
    }

}
