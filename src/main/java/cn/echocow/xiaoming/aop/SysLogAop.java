package cn.echocow.xiaoming.aop;

import cn.echocow.xiaoming.utils.LogUtils;
import cn.echocow.xiaoming.model.entity.SysLog;
import cn.echocow.xiaoming.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 日志记录
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 20:39
 */
@Component
@Aspect
@Slf4j
public class SysLogAop {
    private final HttpServletRequest request;
    private final SysLogService sysLogService;
    private final static Integer MAX_LENGTH = 2048;

    @Autowired
    public SysLogAop(HttpServletRequest request, SysLogService sysLogService) {
        this.request = request;
        this.sysLogService = sysLogService;
    }

    @Pointcut("execution(* cn.echocow.xiaoming.controller.*.*(..))")
    public void sysControllerLog() { }

    @Pointcut("execution(* cn.echocow.xiaoming.base.BaseController.*(..))")
    public void sysBaseControllerLog() { }

    @Pointcut("execution(* cn.echocow.xiaoming.controller.*.getControllerClass(..))")
    public void sysControllerGetClassLog() { }

    @Around("(sysControllerLog() || sysBaseControllerLog()) && !sysControllerGetClassLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        SysLog sysLog = LogUtils.logBuilder(request);
        sysLog.setClassMethod(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        sysLog.setArgs(StringUtils.left(Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(",")), MAX_LENGTH - 5));
        sysLog.setLevel(String.valueOf(LogLevel.INFO.ordinal()));
        Object proceed = proceedingJoinPoint.proceed();
        if (proceed == null) {
            sysLogService.save(sysLog);
            log.info(sysLog.toString());
            return null;
        }
        sysLog.setResult(StringUtils.left(proceed.toString(), MAX_LENGTH - 5));
        sysLogService.save(sysLog);
        log.info(sysLog.toString());
        return proceed;
    }
}
