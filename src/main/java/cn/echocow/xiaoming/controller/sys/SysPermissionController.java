package cn.echocow.xiaoming.controller.sys;

import cn.echocow.xiaoming.entity.sys.SysPermission;
import cn.echocow.xiaoming.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping("/sysMenus")
public class SysPermissionController {
    private final SysPermissionService sysPermissionService;

    @Autowired
    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @GetMapping
    public Resource<?> sysMenus() {
        List<SysPermission> sysPermissions = sysPermissionService.findAll();
        Resource<List<SysPermission>> resource = new Resource<>(sysPermissions);
        resource.add(
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(
                        this.getClass()).sysMenus()).withSelfRel()
        );
        return resource;
    }
}
