package cn.echocow.xiaoming.exception;

/**
 * 文件上传异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-08 22:41
 */
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
