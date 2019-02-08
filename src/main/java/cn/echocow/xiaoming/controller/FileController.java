package cn.echocow.xiaoming.controller;

import ch.qos.logback.core.util.FileSize;
import cn.echocow.xiaoming.entity.File;
import cn.echocow.xiaoming.entity.Homework;
import cn.echocow.xiaoming.entity.Task;
import cn.echocow.xiaoming.exception.FileSizeException;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.TaskService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Objects;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${spring.application.upload-path}")
    private String folder;
    private final FileService fileService;
    private final TaskService taskService;

    @Autowired
    public FileController(FileService fileService, TaskService taskService) {
        this.fileService = fileService;
        this.taskService = taskService;
    }

    public Class getControllerClass() {
        return this.getClass();
    }

    /**
     * 文件上传操作
     *
     * @param file 文件
     * @return http 响应
     * @throws IOException io 异常
     */
    @PostMapping
    public HttpEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("task") Long taskId) throws IOException {
        String fileType = "";

        // 获取后缀
        int index = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (index != -1) {
            fileType = file.getOriginalFilename().substring(index + 1);
        }

        // 本地文件
        java.io.File localFile = new java.io.File(folder, Instant.now().toString() + "." + fileType);

        // 数据库的文件
        File newFile = new File();
        newFile.setName(localFile.getName());
        newFile.setOldName(file.getOriginalFilename());

        // 文件大小限制
        Task task = taskService.findById(taskId);
        Homework homework = task.getHomework();
        FileSize fileSize = FileSize.valueOf(homework.getSize());
        FileSize nowSize = new FileSize(file.getSize());
        if (fileSize.getSize() < nowSize.getSize()) {
            throw new FileSizeException("文件大小不合法！");
        }
        newFile.setSize(nowSize.toString());
        newFile.setType(fileType);
        newFile.setTask(task);

        // 保存文件
        file.transferTo(localFile);
        return new ResponseEntity<>(new RestResource<>(fileService.save(newFile), getControllerClass()), HttpStatus.CREATED);
    }

    /**
     * 文件下载操作
     *
     * @param id       下载文件 id
     * @param response http 响应
     */
    @GetMapping("/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        File file = fileService.findById(id);
        try (
                InputStream inputStream = new FileInputStream(new java.io.File(folder, file.getName()));
                OutputStream outputStream = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.setHeader("content-type", "application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getOldName() + file.getType(), "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
