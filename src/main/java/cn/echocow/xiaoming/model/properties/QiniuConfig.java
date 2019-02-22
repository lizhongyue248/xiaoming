package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:48
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.qiniu")
public class QiniuConfig {
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * bucket
     */
    private String bucket;
    /**
     * 地区
     */
    private String area;
    /**
     * domain
     */
    private String domain;
    /**
     * dirName
     */
    private String dirName;
}
