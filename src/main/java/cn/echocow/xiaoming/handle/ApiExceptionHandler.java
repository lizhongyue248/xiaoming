package cn.echocow.xiaoming.handle;

import cn.echocow.xiaoming.exception.InvalidRequestException;
import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.resource.helper.ErrorResource;
import cn.echocow.xiaoming.resource.helper.FieldResource;
import cn.echocow.xiaoming.resource.helper.InvalidErrorResource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 对异常进行拦截然后封装到响应体
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:59
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /**
     * 资源未找到异常
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(ResourceNoFoundException.class)
    public HttpEntity<?> handleNotFoundException(ResourceNoFoundException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.error(errorResource.toString());
        return new ResponseEntity<>(errorResource, HttpStatus.NOT_FOUND);
    }
    /**
     * 无效的请求异常
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(InvalidRequestException.class)
    public HttpEntity<?> handleInvalidRequestException(InvalidRequestException e) {
        Errors errors = e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            fieldResources.add(
                    new FieldResource(fieldError.getObjectName(),
                            fieldError.getField(),
                            fieldError.getCode(),
                            fieldError.getDefaultMessage())
            );
        }
        InvalidErrorResource invalidErrorResource = new InvalidErrorResource(e.getMessage(), fieldResources);
        log.error(invalidErrorResource.toString());
        return new ResponseEntity<>(invalidErrorResource, HttpStatus.BAD_REQUEST);
    }

    /**
     * 资源已经存在
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(ResourceExistException.class)
    public HttpEntity<?> handleExistException(ResourceExistException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return new ResponseEntity<>(errorResource, HttpStatus.CONFLICT);
    }

    /**
     * 请求方法不支持异常
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpEntity handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return  new ResponseEntity<>(errorResource, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 其它异常统一为 500
     *
     * @param e 其它异常
     * @return http 响应
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<?> handleException(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
