package cn.echocow.xiaoming.model.properties;

import lombok.Data;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:48
 */
@Data
@SuppressWarnings("all")
public class QiniuConfig {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String area;
    private String domain;
    private String dirName;
}
