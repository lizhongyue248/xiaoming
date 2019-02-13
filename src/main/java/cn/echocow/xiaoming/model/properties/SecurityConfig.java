package cn.echocow.xiaoming.model.properties;

import lombok.Data;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:59
 */
@Data
@SuppressWarnings("all")
public class SecurityConfig {
    private String clientId;
    private String clientSecret;
    private String resourceId;
    private String jwtSigningKey;
}
