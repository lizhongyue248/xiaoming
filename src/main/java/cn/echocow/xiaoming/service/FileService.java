package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.Task;

import java.util.Collection;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface FileService extends BaseService<File> {

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
    void deleteFiles(Collection<File> files);

    /**
     * 通过 name 查询
     *
     * @param studentId studentId
     * @param taskId taskId
     * @return 文件
     */
    File findByStudentIdAndTaskId(Long studentId, Long taskId);

    /**
     * 通过 task 和 students 筛选没交作业的学生
     *
     * @param task task
     * @param students 学生
     * @return 结果
     */
    List<Student> findNoFinishedByTaskAndStudents(Task task, List<Student> students);
}
