package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping("/sysUsers")
public class SysUserController extends BaseController<SysUser, SysUserService> {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

    @Override
    @GetMapping("/{id}")
    public HttpEntity<?> getResource(@PathVariable Long id) {
        RestResource<SysUser> resource = new RestResource<>(sysUserService.findById(id), getControllerClass());
        resource.setOthers(resource.getEntity().getRoles());
        return ResponseEntity.ok(resource);
    }

}
