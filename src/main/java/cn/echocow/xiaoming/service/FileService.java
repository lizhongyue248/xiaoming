package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.File;
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


    /**
     * 删除列表的文件
     *
     * @param files 列表
     */
    void deleteFiles(List<File> files);

    /**
     * 通过 name 查询
     *
     * @param name name
     * @return 文件
     */
    File findByName(String name);
}
