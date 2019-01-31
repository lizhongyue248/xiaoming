package cn.echocow.xiaoming.exception;

/**
 * 资源已经存在异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 21:19
 */
public class ResourceExistException extends RuntimeException {
    public ResourceExistException(String message) {
        super(message);
    }
}
