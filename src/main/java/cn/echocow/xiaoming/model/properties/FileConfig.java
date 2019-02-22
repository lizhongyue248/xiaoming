package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 18:04
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.file")
public class FileConfig {
    /**
     * 文件上传路径
     */
    private String uploadPath;
    /**
     * 上传类型
     */
    private String uploadType;
}
