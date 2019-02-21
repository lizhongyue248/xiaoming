package cn.echocow.xiaoming.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 18:04
 */
@Data
@ConfigurationProperties(prefix = "application.file")
@SuppressWarnings("all")
public class FileConfig {
    private String uploadPath;
    private String uploadType;
}
