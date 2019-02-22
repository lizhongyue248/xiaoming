package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.base.BaseController;
import cn.echocow.xiaoming.exception.BadRequestException;
import cn.echocow.xiaoming.model.entity.*;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.resource.RestResources;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.StudentService;
import cn.echocow.xiaoming.service.SysUserService;
import cn.echocow.xiaoming.service.TaskService;
import cn.echocow.xiaoming.utils.SubMailUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController extends BaseController<Task, TaskService> {

    private final FileService fileService;
    private final TaskService taskService;
    private final StudentService studentService;
    private final SysUserService sysUserService;
    private final SubMailUtils subMailUtils;

    @Autowired
    public TaskController(FileService fileService, TaskService taskService, StudentService studentService, SysUserService sysUserService, SubMailUtils subMailUtils) {
        this.fileService = fileService;
        this.taskService = taskService;
        this.studentService = studentService;
        this.sysUserService = sysUserService;
        this.subMailUtils = subMailUtils;
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
    public HttpEntity<?> taskFiles(@PathVariable Long id) {
        return ResponseEntity.ok(new RestResources<>(fileService.findAllByTask(id)));
    }

    /**
     * 对当前 task 下的所有学生发送短信提醒
     *
     * @param id task id
     * @return http 响应
     */
    @GetMapping("/message/{id}")
    public HttpEntity<?> taskTip(@PathVariable Long id){
        Task task = taskService.getCompleteById(id);
        long minutes = Duration.between(LocalDateTime.now(), task.getEndTime()).toMinutes();
        if (minutes < 3){
            throw new BadRequestException("作业关闭时间小于 3 分钟或已经超时");
        }
        Classroom classroom = task.getClassroom();
        Homework homework = task.getHomework();
        List<Student> students = studentService.findByClassroom(classroom);
        List<Student> noFinished = fileService.findNoFinishedByTaskAndStudents(task, students);
        List<SysUser> users =  sysUserService.findByStudents(noFinished);
        JSONObject vars = new JSONObject();
        vars.put("h", " " + homework.getName() + " ");
        vars.put("t", minutes + " 分钟");
        subMailUtils.sendHomeworkTipMessage(users,vars);
        return ResponseEntity.noContent().build();
    }
}
