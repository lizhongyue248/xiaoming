package cn.echocow.xiaoming.exception;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-22 13:10
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
