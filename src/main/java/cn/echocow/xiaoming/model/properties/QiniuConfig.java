package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 七牛云配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:48
 */
@Data
@ConfigurationProperties(prefix = "application.qiniu")
@SuppressWarnings("all")
public class QiniuConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String area;
    private String domain;
    private String dirName;
}
