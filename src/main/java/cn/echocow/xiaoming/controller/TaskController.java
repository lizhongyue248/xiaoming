package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.entity.Task;
import cn.echocow.xiaoming.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping("/tasks")
public class TaskController extends BaseController<Task, TaskService> {

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

}
