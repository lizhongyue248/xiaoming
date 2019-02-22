package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-22 01:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.message")
public class MessageConfig {
    /**
     * appid
     */
    private String appId;
    /**
     * appkey
     */
    private String appKey;
    /**
     * 注册模板
     */
    private String registerTemplate;
    /**
     * 作业提醒模板
     */
    private String homeworkTipTemplate;
}
