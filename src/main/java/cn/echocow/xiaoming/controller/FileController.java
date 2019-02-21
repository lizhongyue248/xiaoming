package cn.echocow.xiaoming.controller;

import ch.qos.logback.core.util.FileSize;
import cn.echocow.xiaoming.exception.FileUploadException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.model.entity.*;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.enums.UploadMethod;
import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import cn.echocow.xiaoming.resource.ApplicationResource;
import cn.echocow.xiaoming.resource.PageSimple;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.resource.RestResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.StudentService;
import cn.echocow.xiaoming.service.TaskService;
import cn.echocow.xiaoming.utils.FileUtils;
import cn.echocow.xiaoming.utils.QiniuUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private String separator = java.io.File.separator;
    private static final String CHARSET = Charset.forName("utf8").name();
    private final FileService fileService;
    private final TaskService taskService;
    private final StudentService studentService;
    private final FileUtils fileUtils;
    private final QiniuUtils qiniuUtils;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public FileController(FileService fileService, TaskService taskService, StudentService studentService, FileUtils fileUtils, QiniuUtils qiniuUtils, ApplicationProperties applicationProperties) {
        this.fileService = fileService;
        this.taskService = taskService;
        this.studentService = studentService;
        this.fileUtils = fileUtils;
        this.qiniuUtils = qiniuUtils;
        this.applicationProperties = applicationProperties;
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
        // 本地存储
        String fileType = "";
        // 获取后缀
        int index = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (index != -1) {
            fileType = file.getOriginalFilename().substring(index + 1);
        }
        // 文件类型
        Task task = taskService.getCompleteById(taskId);
        Classroom classroom = Optional.ofNullable(task.getClassroom()).orElseThrow(
                () -> new ResourceNoFoundException("the task do not have classroom!"));
        Homework homework = Optional.ofNullable(task.getHomework()).orElseThrow(
                () -> new ResourceNoFoundException("the task do not have homework!"));
        if (!"*".equals(homework.getType()) && !homework.getType().contains(fileType)) {
            throw new FileUploadException("文件类型不合法！");
        }

        // 设置文件路径和名称
        Student student = studentService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String pathChild = separator + classroom.getName() + "_" + homework.getName() + "_" + task.getName() + separator;
        String fileName = new String((student.getName() + "_" + student.getNo() + "." + fileType).getBytes(StandardCharsets.UTF_8));
        java.io.File folder = new java.io.File(applicationProperties.getFile().getUploadPath() + pathChild);
        if (!folder.exists()) {
            int i = 0;
            while (i < 3) {
                if (folder.mkdirs()) {
                    break;
                }
                i++;
            }
        }
        // 本地文件
        java.io.File localFile = new java.io.File(folder, fileName);
        // 数据库的文件
        File newFile = fileService.findByStudentIdAndTaskId(student.getId(), taskId);
        java.io.File oldFile = null;
        if (newFile.getId() != null) {
            oldFile = new java.io.File(newFile.getDirName(), newFile.getName());
        }
        newFile.setName(localFile.getName());
        newFile.setOldName(file.getOriginalFilename());
        // 文件大小限制
        FileSize fileSize = FileSize.valueOf(homework.getSize());
        FileSize nowSize = new FileSize(file.getSize());
        if (fileSize.getSize() < nowSize.getSize()) {
            throw new FileUploadException("文件大小不合法！");
        }
        newFile.setSize(nowSize.toString());
        newFile.setType(fileType);
        newFile.setTaskId(task.getId());
        newFile.setStudentId(student.getId());
        // 如果是七牛云存储
        if (UploadMethod.QINIU.match(applicationProperties.getFile().getUploadType())) {
            DefaultPutRet defaultPutRet = qiniuUtils.upload(file, fileName, pathChild);
            newFile.setDirName(applicationProperties.getQiniu().getDirName() + pathChild.replace(separator, "/"));
            if (!fileService.save(newFile)) {
                throw new RuntimeException("Save failed!");
            }
            newFile.setRemark(defaultPutRet.hash);
            return new ResponseEntity<>(new RestResource<>(newFile, getControllerClass()), HttpStatus.CREATED);
        }
        newFile.setDirName(applicationProperties.getFile().getUploadPath() + pathChild);
        // 删除原来的文件
        if (oldFile != null && oldFile.exists()) {
            if (!oldFile.delete()) {
                log.warn("last file delete failed!" + oldFile.getAbsolutePath());
            }
        }
        // 保存文件
        file.transferTo(localFile);
        if (!fileService.saveOrUpdate(newFile)) {
            throw new RuntimeException("Save Or Update failed!");
        }
        return new ResponseEntity<>(new RestResource<>(newFile, getControllerClass()), HttpStatus.CREATED);
    }

    /**
     * 文件下载操作
     *
     * @param id       下载文件 id
     * @param response http 响应
     */
    @GetMapping("/{id}")
    public HttpEntity<?> download(@PathVariable Long id, HttpServletResponse response) throws Exception {
        File file = fileService.getById(id);
        if (UploadMethod.QINIU.match(applicationProperties.getFile().getUploadType())) {
            return qiniuUtils.download(file);
        }
        try (
                InputStream inputStream = new FileInputStream(new java.io.File(file.getDirName(), file.getName()));
                OutputStream outputStream = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.setHeader("content-type", "application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName() + file.getType(), CHARSET));
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     * @return http 响应
     * @throws QiniuException 七牛云异常
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) throws QiniuException {
        File file = fileService.getById(id);
        if (UploadMethod.QINIU.match(applicationProperties.getFile().getUploadType())) {
            qiniuUtils.deleteOne(file);
        } else {
            fileUtils.localFileDelete(file);
        }
        fileService.removeById(id);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }

    /**
     * 删除指定 task 下的所有文件
     * 七牛云下，如果超过 1000 文件则无法删除
     * 现阶段默认不存在此情况
     *
     * @param id task id
     * @return http 响应
     * @throws QiniuException 七牛云异常
     */
    @DeleteMapping("/tasks/{id}")
    public HttpEntity<?> deleteByTask(@PathVariable Long id) throws QiniuException {
        return deleteFiles(fileService.findAllByTask(id));
    }

    /**
     * 删除多个文件
     * 七牛云下，如果超过 1000 文件则无法删除
     * 现阶段默认不存在此情况
     *
     * @param files 要删除的文件
     * @return http 响应
     * @throws QiniuException 七牛云异常
     */
    @DeleteMapping
    public HttpEntity<?> deleteFiles(@RequestBody String files) throws QiniuException {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(files).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<Long> ids = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            Long id = gson.fromJson(jsonElement, Long.class);
            ids.add(id);
        });
        return deleteFiles(fileService.listByIds(ids));
    }

    /**
     * 删除文件
     *
     * @param files 文件列表
     * @return http 响应
     * @throws QiniuException 七牛云异常
     */
    private HttpEntity<?> deleteFiles(Collection<File> files) throws QiniuException {
        if (UploadMethod.QINIU.match(applicationProperties.getFile().getUploadType())) {
            qiniuUtils.delete(files);
        } else {
            fileUtils.localFilesDelete(files);
        }
        fileService.deleteFiles(files);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }

    /**
     * 获取所有 文件信息
     *
     * @param page 页码
     * @param size 大小
     * @return http 响应
     */
    @GetMapping
    @PageResult
    public HttpEntity<?> files(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size) {
        if (page == null || size == null || page <= 0 || size <= 0) {
            return ResponseEntity.ok(new Resources<>(fileService.list().stream()
                    .map(entity -> new RestResource<>(entity, getControllerClass()))
                    .collect(Collectors.toList())));
        }
        IPage<File> result = fileService.page(new Page<>(page, size));
        RestResources<RestResource> resources = new RestResources<>();
        resources.setPage(new PageSimple(result.getSize(), result.getCurrent(), result.getTotal(), result.getPages()));
        resources.setCollect(result.getRecords().stream()
                .map(file -> new RestResource<>(file, getControllerClass()))
                .collect(Collectors.toList()));
        return ResponseEntity.ok(resources);
    }
}
