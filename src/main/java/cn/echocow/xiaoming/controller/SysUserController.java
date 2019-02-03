package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.web.bind.annotation.*;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping("/sysUsers")
public class SysUserController extends BaseController<SysUser, SysUserService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
