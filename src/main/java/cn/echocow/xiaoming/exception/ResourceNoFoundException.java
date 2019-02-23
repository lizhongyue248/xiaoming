package cn.echocow.xiaoming.exception;


/**
 * 资源未找到异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:36
 */
public class ResourceNoFoundException extends BadRequestException {
    public ResourceNoFoundException(String message) {
        super(message);
    }

    public ResourceNoFoundException(Long id) {
        super(id);
    }

    public ResourceNoFoundException(String name, Long id) {
        super(name, id);
    }

    public ResourceNoFoundException(String name, String field, Object content) {
        super(name, field, content);
    }
}
