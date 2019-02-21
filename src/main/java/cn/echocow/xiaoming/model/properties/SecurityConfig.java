package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:59
 */
@Data
@ConfigurationProperties(prefix = "application.security")
@SuppressWarnings("all")
public class SecurityConfig {
    private String clientId;
    private String clientSecret;
    private String resourceId;
    private String jwtSigningKey;
}
