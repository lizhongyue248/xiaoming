package cn.echocow.xiaoming.exception;

import com.sun.javafx.binding.StringFormatter;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-22 13:10
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    BadRequestException(Long id) {
        super(StringFormatter.format("the resource by id %s not found!", id).getValueSafe());
    }

    BadRequestException(String name, Long id) {
        super(StringFormatter.format("the %s by id %s not found!", name, id).getValueSafe());
    }

    BadRequestException(String name, String field, Object content) {
        super(StringFormatter.format("the %s by %s %s not found!", name, field, content.toString()).getValueSafe());
    }
}
