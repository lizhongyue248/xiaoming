package cn.echocow.xiaoming.exception;

/**
 * service 异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-21 17:07
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
