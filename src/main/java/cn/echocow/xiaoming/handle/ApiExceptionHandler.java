package cn.echocow.xiaoming.handle;

import cn.echocow.xiaoming.exception.*;
import cn.echocow.xiaoming.utils.LogUtils;
import cn.echocow.xiaoming.resource.ErrorResource;
import cn.echocow.xiaoming.resource.FieldResource;
import cn.echocow.xiaoming.resource.InvalidErrorResource;
import cn.echocow.xiaoming.service.SysLogService;
import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 对异常进行拦截然后封装到响应体
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:59
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private final HttpServletRequest request;
    private final SysLogService sysLogService;

    @Autowired
    public ApiExceptionHandler(HttpServletRequest request, SysLogService sysLogService) {
        this.request = request;
        this.sysLogService = sysLogService;
    }

    /**
     * 资源未找到异常
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(ResourceNoFoundException.class)
    public HttpEntity<?> handleNotFoundException(ResourceNoFoundException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.warn(errorResource.toString() +
                sysLogService.save(LogUtils.exceptionWarnBuilder(request, e)));
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
        log.warn(invalidErrorResource.toString() +
                sysLogService.save(LogUtils.exceptionWarnBuilder(request, e)));
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
        log.warn(errorResource.toString() +
                sysLogService.save(LogUtils.exceptionWarnBuilder(request, e)));
        return new ResponseEntity<>(errorResource, HttpStatus.CONFLICT);
    }

    /**
     * 文件上传和错误请求
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler({FileUploadException.class, BadRequestException.class})
    public HttpEntity<?> handleFileSizeException(FileUploadException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.warn(errorResource.toString() +
                sysLogService.save(LogUtils.exceptionWarnBuilder(request, e)));
        return new ResponseEntity<>(errorResource, HttpStatus.BAD_REQUEST);
    }

    /**
     * 请求方法不支持异常
     *
     * @param e 异常
     * @return http 响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpEntity<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.warn(errorResource.toString() +
                sysLogService.save(LogUtils.exceptionWarnBuilder(request, e)));
        return new ResponseEntity<>(errorResource, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 七牛云异常
     *
     * @param e 七牛云异常
     * @return http 响应
     */
    @ExceptionHandler(QiniuException.class)
    public HttpEntity<?> handleQiniuException(QiniuException e) {
        ErrorResource errorResource = new ErrorResource(e.response.toString());
        log.error(sysLogService.save(LogUtils.exceptionErrorBuilder(request, e)) + "");
        return new ResponseEntity<>(errorResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 必填参数异常
     *
     * @param e present
     * @return http 响应
     */
    @ExceptionHandler({MissingServletRequestParameterException.class,
            UnapprovedClientAuthenticationException.class})
    public HttpEntity<?> handleServletRequestParameterException(Exception e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.error(sysLogService.save(LogUtils.exceptionErrorBuilder(request, e)) + "");
        return new ResponseEntity<>(errorResource, HttpStatus.BAD_REQUEST);
    }

    /**
     * 其它异常统一为 500
     *
     * @param e 其它异常
     * @return http 响应
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<?> handleException(Exception e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        log.error(sysLogService.save(LogUtils.exceptionErrorBuilder(request, e)) + "");
        return new ResponseEntity<>(errorResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
