package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.repository.TaskRepository;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface TaskService extends BaseService<Task, Long, TaskRepository> {
}
