package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.Task;
import cn.echocow.xiaoming.repository.TaskRepository;
import cn.echocow.xiaoming.service.TaskService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"task"}, keyGenerator = "cacheKeyGenerator")
public class TaskServiceImpl extends BaseServiceImpl<Task, Long, TaskRepository> implements TaskService {
}
