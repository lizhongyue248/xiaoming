package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.model.entity.SysPermission;
import cn.echocow.xiaoming.service.SysPermissionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping(value = "/sysMenus", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysPermissionController extends BaseController<SysPermission, SysPermissionService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
