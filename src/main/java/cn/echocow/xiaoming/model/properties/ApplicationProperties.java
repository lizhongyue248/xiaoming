package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    /**
     * 应用名称
     */
    private String name;
    /**
     * 七牛云配置相关
     */
    private QiniuConfig qiniu = new QiniuConfig();
    /**
     * 安全配置相关
     */
    private SecurityConfig security = new SecurityConfig();
    /**
     * 文件配置相关
     */
    private FileConfig file = new FileConfig();
    /**
     * 验证配置相关
     */
    private ValidateConfig validate = new ValidateConfig();
}
