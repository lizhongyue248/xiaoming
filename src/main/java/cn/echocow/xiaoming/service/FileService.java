package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.entity.File;
import cn.echocow.xiaoming.entity.Task;
import cn.echocow.xiaoming.repository.FileRepository;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface FileService extends BaseService<File, Long, FileRepository> {

    /**
     * 通过 task id 查询所有结果
     *
     * @param taskId task id
     * @return 结果
     */
    List<File> findAllByTask(Long taskId);
}
