package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.entity.Task;
import cn.echocow.xiaoming.resource.RestResources;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final FileService fileService;

    @Autowired
    public TaskController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }

    /**
     * 查询当前 task 下的所有文件
     *
     * @param id task id
     * @return http 响应
     */
    @GetMapping("/files/{id}")
    public HttpEntity<?> tasks(@PathVariable Long id) {
        return ResponseEntity.ok(new RestResources<>(fileService.findAllByTask(id)));
    }
}
