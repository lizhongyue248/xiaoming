package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-14 15:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.validate")
public class ValidateConfig {
    /**
     * 邮箱验证码最小长度
     */
    private Integer emailCodeMinLength;
    /**
     * 邮箱验证码最大长度
     */
    private Integer emailCodeMaxLength;
    /**
     * 短信验证码最小长度
     */
    private Integer mobileCodeMinLength;
    /**
     * 短信验证码最大长度
     */
    private Integer mobileCodeMaxLength;
    /**
     * 邮箱验证码有效期，单位：分钟
     */
    private Long emailCodeValidityPeriod;
    /**
     * 手机验证码有效期，单位：分钟
     */
    private Long mobileCodeValidityPeriod;
}
