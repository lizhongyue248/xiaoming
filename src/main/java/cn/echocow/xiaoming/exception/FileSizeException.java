package cn.echocow.xiaoming.exception;

/**
 * 文件大小异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-08 22:41
 */
public class FileSizeException extends RuntimeException {
    public FileSizeException(String message) {
        super(message);
    }
}
