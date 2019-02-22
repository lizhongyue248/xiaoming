package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安全配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:59
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.security")
public class SecurityConfig {
    /**
     * clientId
     */
    private String clientId;
    /**
     * clientSecret
     */
    private String clientSecret;
    /**
     * resourceId
     */
    private String resourceId;
    /**
     * jwtSigningKey
     */
    private String jwtSigningKey;
}
