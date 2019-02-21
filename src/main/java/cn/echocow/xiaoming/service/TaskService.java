package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.Task;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface TaskService extends BaseService<Task> {

    /**
     * 通过 id 获取完整的 task 信息
     *
     * @param id task id
     * @return 结果
     */
    Task getCompleteById(Long id);

}
