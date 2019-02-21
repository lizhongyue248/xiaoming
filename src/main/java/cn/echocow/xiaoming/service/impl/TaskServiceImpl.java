package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.mapper.TaskMapper;
import cn.echocow.xiaoming.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public Task getCompleteById(Long id) {
        return Optional.ofNullable(taskMapper.findFirstCompleteById(id)).orElseThrow(
                () -> new ResourceNoFoundException("task", id)
        );
    }
}
