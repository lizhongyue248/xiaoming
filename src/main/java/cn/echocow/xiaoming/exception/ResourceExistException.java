package cn.echocow.xiaoming.exception;

/**
 * 资源已经存在异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 21:19
 */
public class ResourceExistException extends BadRequestException {
    public ResourceExistException(String message) {
        super(message);
    }

    public ResourceExistException(Long id) {
        super(id);
    }

    public ResourceExistException(String name, Long id) {
        super(name, id);
    }

    public ResourceExistException(String name, String field, Object content) {
        super(name, field, content);
    }
}
