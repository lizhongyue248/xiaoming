package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.entity.Homework;
import cn.echocow.xiaoming.service.HomeworkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping("/homework")
public class HomeworkController extends BaseController<Homework, HomeworkService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
