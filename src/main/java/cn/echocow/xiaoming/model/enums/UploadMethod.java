package cn.echocow.xiaoming.model.enums;

/**
 * 上传方式
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-08 23:32
 */
public enum UploadMethod {
    /**
     * 本地文件存储
     */
    LOCAL,
    /**
     * 七牛云存储
     */
    QINIU;

    /**
     * 上传方式匹配
     *
     * @param name 方法名称
     * @return 匹配结果
     */
    public boolean match(String name) {
        return name().equals(name.toUpperCase());
    }

}
