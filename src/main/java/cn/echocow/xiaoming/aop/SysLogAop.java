package cn.echocow.xiaoming.aop;

import cn.echocow.xiaoming.Utils.LogUtil;
import cn.echocow.xiaoming.entity.sys.SysLog;
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

    @Pointcut("execution(* cn.echocow.xiaoming.controller.*.*.*(..))")
    public void sysControllerLog() { }

    @Pointcut("execution(* cn.echocow.xiaoming.controller.MainController.*(..))")
    public void sysMainControllerLog() { }

    @Around("sysMainControllerLog() || sysControllerLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        SysLog sysLog = LogUtil.logBuilder(request);
        sysLog.setMethod(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        sysLog.setArgs(StringUtils.left(Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(",")), MAX_LENGTH - 5));
        sysLog.setLevel(LogLevel.INFO.ordinal());
        Object proceed = proceedingJoinPoint.proceed();
        sysLog.setResult(StringUtils.left(proceed.toString(), MAX_LENGTH - 5));
        log.info(sysLogService.save(sysLog).toString());
        return proceed;
    }
}
