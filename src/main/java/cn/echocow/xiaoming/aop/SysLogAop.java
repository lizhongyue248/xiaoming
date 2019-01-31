package cn.echocow.xiaoming.aop;

import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.service.SysLogService;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

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

    @Autowired
    public SysLogAop(HttpServletRequest request, SysLogService sysLogService) {
        this.request = request;
        this.sysLogService = sysLogService;
    }

    @Pointcut("execution(* cn.echocow.xiaoming.controller.*.*.*(..))")
    public void sysControllerLog() {
    }

    @Pointcut("execution(* cn.echocow.xiaoming.controller.MainController.*(..))")
    public void sysMainControllerLog() {
    }

    @Before("sysMainControllerLog() || sysControllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        SysLog sysLog = new SysLog();
        sysLog.setIp(request.getRemoteAddr());
        sysLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLog.setUrl(request.getRequestURL().toString());
        StringBuilder argString = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg: args) {
            argString.append(arg.toString()).append(",");
        }
        sysLog.setArgs(argString.toString());
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        sysLog.setBrowser(userAgent.getBrowser().getName() + userAgent.getBrowserVersion());
        sysLog.setOs(operatingSystem.getName());
        sysLog.setDeviceType(operatingSystem.getDeviceType().getName());
        sysLog.setRequestMethod(request.getMethod());
        sysLog.setLevel(LogLevel.INFO.ordinal());
        sysLogService.save(sysLog);
        log.info(sysLog.toString());
    }

    @After("sysMainControllerLog() || sysControllerLog()")
    public void doAfter() {
        log.info("after");
    }

    @AfterReturning(pointcut = "sysMainControllerLog() || sysControllerLog()", returning = "result")
    public void doAfterReturning(Object result) {
        log.info("result" + result);
    }

}
