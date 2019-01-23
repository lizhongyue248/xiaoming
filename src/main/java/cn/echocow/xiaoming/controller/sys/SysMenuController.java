package cn.echocow.xiaoming.controller.sys;

import cn.echocow.xiaoming.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:06
 */
@RequestMapping("/sys")
@RestController
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
    @RequestMapping("/hello")
    public HttpEntity<?> hello() {
        return new ResponseEntity<>(sysMenuService.findAll(), HttpStatus.OK);
    }
}
