package cn.echocow.xiaoming.model.properties;

import lombok.Data;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 18:04
 */
@Data
@SuppressWarnings("all")
public class FileConfig {
    private String uploadPath;
    private String uploadType;
}
