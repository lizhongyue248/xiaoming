package cn.echocow.xiaoming.Utils;

import cn.echocow.xiaoming.entity.sys.SysLog;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.boot.logging.LogLevel;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单日志封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 23:59
 */
public class LogUtil {
    /**
     * 生成简单日志封装
     *
     * @param request 请求
     * @return 日志
     */
    public static SysLog logBuilder(HttpServletRequest request) {
        SysLog sysLog = new SysLog();
        sysLog.setIp(request.getRemoteAddr());
        sysLog.setUrl(request.getRequestURL().toString());
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        sysLog.setBrowser(userAgent.getBrowser().getName() + userAgent.getBrowserVersion());
        sysLog.setOs(operatingSystem.getName());
        sysLog.setDeviceType(operatingSystem.getDeviceType().getName());
        sysLog.setRequestMethod(request.getMethod());
        return sysLog;
    }

    /**
     * 错误异常日志封装
     *
     * @param request 请求
     * @param e 异常
     * @return 日志
     */
    public static SysLog exceptionErrorBuilder(HttpServletRequest request, Exception e) {
        SysLog sysLog = logBuilder(request);
        sysLog.setLevel(LogLevel.ERROR.ordinal());
        sysLog.setResult(e.getMessage());
        return sysLog;
    }

    /**
     * 警告日志封装
     *
     * @param request 请求
     * @param e 异常
     * @return 日志
     */
    public static SysLog exceptionWarnBuilder(HttpServletRequest request, Exception e) {
        SysLog sysLog = logBuilder(request);
        sysLog.setLevel(LogLevel.WARN.ordinal());
        sysLog.setResult(e.getMessage());
        return sysLog;
    }
}
