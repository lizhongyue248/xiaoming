package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.FileRepository;
import cn.echocow.xiaoming.repository.TaskRepository;
import cn.echocow.xiaoming.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"file"}, keyGenerator = "cacheKeyGenerator")
public class FileServiceImpl extends BaseServiceImpl<File, Long, FileRepository> implements FileService {

    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, TaskRepository taskRepository) {
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<File> findAllByTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        return fileRepository.findAllByTask(taskOptional.orElseThrow(() ->
                new ResourceNoFoundException("task no found....")));
    }

    @Override
    public void deleteFiles(List<File> files) {
        List<Long> ids = new ArrayList<>(files.size());
        for (File file : files) {
            ids.add(file.getId());
        }
        fileRepository.deleteBatch(ids);
    }

    @Override
    public File findByName(String name) {
        return fileRepository.findFirstByName(name).orElse(new File());
    }
}
