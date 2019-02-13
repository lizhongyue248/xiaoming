package cn.echocow.xiaoming.utils;

import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.enums.QiniuConfig;
import cn.echocow.xiaoming.model.properties.ApplicationProperties;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 七牛云工具类
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-11 11:24
 */
@Slf4j
@Component
public class QiniuUtils {
    private final ApplicationProperties applicationProperties;
    private static final String CHARSET = Charset.forName("utf8").name();

    @Autowired
    public QiniuUtils(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * 上传凭证
     *
     * @return 上传凭证
     */
    private Auth createAuth() {
        return Auth.create(applicationProperties.getQiniu().getAccessKey(),
                applicationProperties.getQiniu().getSecretKey());
    }

    /**
     * 创建简单 七牛云 配置信息
     *
     * @return 配置信息
     */
    private Configuration configurationQiNiu() {
        String area = applicationProperties.getQiniu().getArea();
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
     * 七牛云 文件上传
     *
     * @param file      要上传的文件
     * @param localFile 本地文件
     * @param pathChild 子目录
     * @return DefaultPutRet
     */
    public DefaultPutRet upload(MultipartFile file, java.io.File localFile, String pathChild) throws IOException {
        UploadManager uploadManager = new UploadManager(configurationQiNiu());
        String token = createAuth().uploadToken(applicationProperties.getQiniu().getBucketName());
        Response response = uploadManager.put(file.getBytes(), applicationProperties.getQiniu().getDirName() + pathChild + localFile.getName(), token);
        return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }

    /**
     * 七牛云 文件下载
     *
     * @param file 要下载的文件
     * @return http 响应
     * @throws URISyntaxException 异常
     */
    public HttpEntity<?> download(File file) throws URISyntaxException, UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI(file.getDirName() + URLEncoder.encode(file.getName(), CHARSET))).build();
    }

    /**
     * 七牛云 文件删除
     *
     * @param file 要删除的文件
     * @throws QiniuException 异常
     */
    @Async
    public void deleteOne(File file) throws QiniuException {
        BucketManager bucketManager = new BucketManager(createAuth(), configurationQiNiu());
        bucketManager.delete(applicationProperties.getQiniu().getBucketName(), file.getDirName() + file.getName());
    }

    /**
     * 七牛云 文件删除，删除多个
     *
     * @param files 多个文件
     * @throws QiniuException 异常
     */
    @Async
    public void delete(List<File> files) throws QiniuException {
        BucketManager bucketManager = new BucketManager(createAuth(), configurationQiNiu());
        String[] keyList = files.stream().map(file -> file.getDirName() + file.getName()).toArray(String[]::new);
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(applicationProperties.getQiniu().getBucketName(), keyList);
        Response response = bucketManager.batch(batchOperations);
        BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
        for (int i = 0; i < keyList.length; i++) {
            BatchStatus status = batchStatusList[i];
            String key = keyList[i];
            if (status.code == HttpStatus.OK.value()) {
                log.info(key + " delete success!");
            } else {
                log.warn(key + " delete failed! " + status.data.error);
            }
        }
    }
}
