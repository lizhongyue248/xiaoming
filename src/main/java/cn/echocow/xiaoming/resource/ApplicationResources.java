package cn.echocow.xiaoming.resource;

import cn.echocow.xiaoming.controller.sys.SysPermissionController;
import cn.echocow.xiaoming.controller.sys.SysRoleController;
import cn.echocow.xiaoming.controller.sys.SysUserController;
import cn.echocow.xiaoming.resource.helper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.Arrays;
import java.util.List;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 00:10
 */
@Getter
@Setter
public class ApplicationResources<T> extends Resources<T> {
    private PageInfo page;
    private List<T> collect;

    public ApplicationResources(List<T> collect) {
        this.collect = collect;
        add(ControllerLinkBuilder.linkTo(SysPermissionController.class).withRel("sys_menu"));
        add(ControllerLinkBuilder.linkTo(SysUserController.class).withRel("sys_user"));
        add(ControllerLinkBuilder.linkTo(SysRoleController.class).withRel("sys_role"));
    }


}
