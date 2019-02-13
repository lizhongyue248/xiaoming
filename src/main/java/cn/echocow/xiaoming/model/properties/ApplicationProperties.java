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
    private String name;
    private QiniuConfig qiniu = new QiniuConfig();
    private SecurityConfig security = new SecurityConfig();
    private FileConfig file = new FileConfig();
}
