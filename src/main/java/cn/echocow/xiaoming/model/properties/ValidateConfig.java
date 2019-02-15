package cn.echocow.xiaoming.model.properties;

import lombok.Data;

/**
 * 验证配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-14 15:55
 */
@Data
@SuppressWarnings("all")
public class ValidateConfig {
    private Integer emailCodeMinLength;
    private Integer emailCodeMaxLength;
    private Integer mobileCodeMinLength;
    private Integer mobileCodeMaxLength;
    private Long emailCodeValidityPeriod;
    private Long mobileCodeValidityPeriod;
}
