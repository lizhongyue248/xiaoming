package cn.echocow.xiaoming.controller;

import ch.qos.logback.core.util.FileSize;
import cn.echocow.xiaoming.model.entity.Classroom;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.entity.Homework;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.model.enums.QiniuConfig;
import cn.echocow.xiaoming.model.enums.UploadMethod;
import cn.echocow.xiaoming.exception.FileUploadException;
import cn.echocow.xiaoming.resource.ApplicationResource;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.TaskService;
import cn.echocow.xiaoming.utils.DateUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
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
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${spring.application.file.upload-path}")
    private String uploadPath;
    @Value("${spring.application.file.upload-type}")
    private String uploadType;
    @Value("${spring.application.qiniu.access-key}")
    private String accessKey;
    @Value("${spring.application.qiniu.secret-key}")
    private String secretKey;
    @Value("${spring.application.qiniu.bucket}")
    private String bucketName;
    @Value("${spring.application.qiniu.area}")
    private String area;
    @Value("${spring.application.qiniu.dir-name}")
    private String dirName;
    @Value("${spring.application.qiniu.domain}")
    private String domain;
    private static final String CHARSET = Charset.forName("utf8").name();
    private static final Integer MAX_FILE_NUM = 1000;
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
        // 本地存储
        String fileType = "";

        // 获取后缀
        int index = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (index != -1) {
            fileType = file.getOriginalFilename().substring(index + 1);
        }

        // 文件类型
        Task task = taskService.findById(taskId);
        Classroom classroom = task.getClassroom();
        Homework homework = task.getHomework();
        if (!homework.getType().contains(fileType)) {
            throw new FileUploadException("文件类型不合法！");
        }
        String pathChild = "/" + classroom.getName() + "/" + task.getName() + "/" + homework.getName() + "/";
        // TODO: 拼接当前用户的信息

        // 本地文件
        java.io.File localFile = new java.io.File(uploadPath + pathChild, URLEncoder.encode(DateUtils.nowString() + "." + fileType, CHARSET));

        // 数据库的文件
        File newFile = new File();
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
        newFile.setTask(task);

        // 如果是七牛云存储
        if (UploadMethod.QINIU.match(uploadType)) {
            UploadManager uploadManager = new UploadManager(configurationQiNiu());
            String token = createAuth().uploadToken(bucketName);
            Response response = uploadManager.put(file.getBytes(), URLEncoder.encode(dirName + pathChild + localFile.getName(), CHARSET), token);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            newFile.setDirName(dirName + pathChild);
            File save = fileService.save(newFile);
            save.setRemark(defaultPutRet.hash);
            return new ResponseEntity<>(new RestResource<>(save, getControllerClass()), HttpStatus.CREATED);
        }
        newFile.setDirName(uploadPath + pathChild);
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
    public HttpEntity<?> download(@PathVariable Long id, HttpServletResponse response) throws Exception {
        File file = fileService.findById(id);
        if (UploadMethod.QINIU.match(uploadType)) {
            return ResponseEntity.status(HttpStatus.FOUND).location(new URI(file.getDirName() + file.getName())).build();
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
        File file = fileService.findById(id);
        if (UploadMethod.QINIU.match(uploadType)) {
            BucketManager bucketManager = new BucketManager(createAuth(), configurationQiNiu());
            bucketManager.delete(bucketName, file.getDirName() + file.getName());
        } else {
            fileDelete(file);
        }
        fileService.deleteById(id);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }

    /**
     * 删除指定 task 下的所有文件
     * 七牛云下，如果超过 1000 文件则无法删除
     * 现阶段默认不存在此情况
     *
     * @param id task id
     * @return http 响应
     */
    @DeleteMapping("/task/{id}")
    public HttpEntity<?> deleteByTask(@PathVariable Long id) throws QiniuException {
        List<File> files = fileService.findAllByTask(id);
        if (UploadMethod.QINIU.match(uploadType)) {
            BucketManager bucketManager = new BucketManager(createAuth(), configurationQiNiu());
            String[] keyList = files.stream().map(file -> file.getDirName() + file.getName()).toArray(String[]::new);
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucketName, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {
                    log.info(key + " delete success!");
                } else {
                    log.warn(key + " delete failed! " + status.data.error);
                }
            }
        } else {
            for (File file : files) {
                fileDelete(file);
            }
        }
        fileService.deleteFiles(files);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }

    /**
     * 上传凭证
     *
     * @return 上传凭证
     */
    private Auth createAuth() {
        return Auth.create(accessKey, secretKey);
    }

    /**
     * 创建简单 七牛云 配置信息
     *
     * @return 配置信息
     */
    private Configuration configurationQiNiu() {
        Configuration config;
        if (QiniuConfig.EAST.match(area)) {
            config = new Configuration(Zone.zone0());
        } else if (QiniuConfig.NORTH.match(area)) {
            config = new Configuration(Zone.zone1());
        } else if (QiniuConfig.SOUTH.match(area)) {
            config = new Configuration(Zone.zone2());
        } else if (QiniuConfig.AMERICA.match(area)) {
            config = new Configuration(Zone.zoneNa0());
        } else if (QiniuConfig.Asia.match(area)) {
            config = new Configuration(Zone.zoneAs0());
        } else {
            config = new Configuration(Zone.autoZone());
        }
        return config;
    }

    /**
     * 删除文件
     *
     * @param file 文件信息
     */
    private void fileDelete(File file) {
        java.io.File localFile = new java.io.File(file.getDirName(), file.getName());
        if (localFile.exists()) {
            int count = 0;
            // 删除重试
            while (count < 2) {
                if (localFile.delete()) {
                    break;
                }
                count++;
            }
        }
    }
}
