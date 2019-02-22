package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.mapper.FileMapper;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public List<File> findAllByTask(Long taskId) {
        return Optional.ofNullable(fileMapper.selectList(new QueryWrapper<File>().lambda().eq(File::getTaskId, taskId)))
                .orElseThrow(() -> new ResourceNoFoundException("task", taskId));
    }

    @Override
    public void deleteFiles(Collection<File> files) {
        List<Long> ids = new ArrayList<>(files.size());
        for (File file : files) {
            ids.add(file.getId());
        }
        fileMapper.deleteBatchIds(ids);
    }

    @Override
    public File findByStudentIdAndTaskId(Long studentId, Long taskId) {
        return Optional.ofNullable(fileMapper.selectOne(
                new QueryWrapper<File>()
                        .lambda()
                        .eq(File::getStudentId, studentId)
                        .eq(File::getTaskId, taskId)))
                .orElse(new File());
    }

    @Override
    public List<Student> findNoFinishedByTaskAndStudents(Task task, List<Student> students) {
        if (students == null || students.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> ids = students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        fileMapper.selectList(new QueryWrapper<File>()
                .lambda()
                .eq(File::getTaskId, task.getId())
                .in(File::getStudentId, ids))
                // 此时 ids 只存放没有交作业的学生
                .forEach(file -> ids.remove(file.getStudentId()));
        return students.stream()
                .filter(student -> ids.contains(student.getId()))
                .collect(Collectors.toList());
    }
}
