package cn.echocow.xiaoming.controller;

import ch.qos.logback.core.util.FileSize;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.entity.Homework;
import cn.echocow.xiaoming.model.entity.Task;
import cn.echocow.xiaoming.model.enums.QiniuConfig;
import cn.echocow.xiaoming.model.enums.UploadMethod;
import cn.echocow.xiaoming.exception.FileUploadException;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.service.FileService;
import cn.echocow.xiaoming.service.TaskService;
import cn.echocow.xiaoming.utils.DateUtil;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
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
import java.util.Objects;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:43
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${spring.application.file.upload-path}")
    private String folder;
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
        Homework homework = task.getHomework();
        if (!homework.getType().contains(fileType)) {
            throw new FileUploadException("文件类型不合法！");
        }

        // 本地文件
        java.io.File localFile = new java.io.File(folder, URLEncoder.encode(DateUtil.nowString() + "." + fileType, CHARSET));

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
            DefaultPutRet defaultPutRet = uploadQiniu(file, localFile);
            File save = fileService.save(newFile);
            save.setRemark(defaultPutRet.hash);
            return new ResponseEntity<>(new RestResource<>(save, getControllerClass()), HttpStatus.CREATED);
        }

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
            return ResponseEntity.status(HttpStatus.FOUND).location(
                    new URI(domain + dirName + URLEncoder.encode(file.getName(), CHARSET))).build();
        }
        try (
                InputStream inputStream = new FileInputStream(new java.io.File(folder, file.getName()));
                OutputStream outputStream = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.setHeader("content-type", "application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getOldName() + file.getType(), CHARSET));
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 七牛云的文件上传
     *
     * @param file      上传的文件
     * @param localFile 本地文件的数据
     * @return 响应数据
     * @throws IOException io 异常
     */
    private DefaultPutRet uploadQiniu(MultipartFile file, java.io.File localFile) throws IOException {
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
        UploadManager uploadManager = new UploadManager(config);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        Response response = uploadManager.put(file.getBytes(), dirName + localFile.getName(), token);
        return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }
}
