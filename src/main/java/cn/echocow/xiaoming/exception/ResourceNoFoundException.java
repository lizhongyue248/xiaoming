package cn.echocow.xiaoming.exception;

import com.sun.javafx.binding.StringFormatter;

import java.io.Serializable;

/**
 * 资源未找到异常
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:36
 */
public class ResourceNoFoundException extends RuntimeException {
    public ResourceNoFoundException(String message) {
        super(message);
    }

    public ResourceNoFoundException(Long id) {
        super(StringFormatter.format("the resource by id %s not found!", id).getValueSafe());
    }

    public ResourceNoFoundException(Serializable id) {
        super(StringFormatter.format("the resource by id %s not found!", id).getValueSafe());
    }

    public ResourceNoFoundException(String name, Long id) {
        super(StringFormatter.format("the %s by id %s not found!", name, id).getValueSafe());
    }

    public ResourceNoFoundException(String name, String field, Object content) {
        super(StringFormatter.format("the %s by %s %s not found!", name, field, content.toString()).getValueSafe());
    }
}
