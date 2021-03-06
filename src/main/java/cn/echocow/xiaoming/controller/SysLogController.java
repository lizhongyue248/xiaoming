package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.model.entity.SysLog;
import cn.echocow.xiaoming.service.SysLogService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping(value = "/sysLogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysLogController extends BaseController<SysLog, SysLogService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
