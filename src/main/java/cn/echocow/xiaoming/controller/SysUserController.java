package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping(value = "/sysUsers", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping
    public HttpEntity<?> saveResource(@Valid @RequestBody SysUser entity, BindingResult bindingResult) {
        if (entity.getPassword() != null) {
            entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        }
        String existUser = sysUserService.existUser(entity);
        if (existUser != null){
            throw new ResourceExistException(existUser);
        }
        return super.saveResource(entity, bindingResult);
    }
}
