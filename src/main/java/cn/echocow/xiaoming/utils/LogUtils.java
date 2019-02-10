package cn.echocow.xiaoming.utils;

import cn.echocow.xiaoming.model.entity.SysLog;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.logging.LogLevel;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 简单日志封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 23:59
 */
public class LogUtils {
    private final static Integer MAX_LEN = 250;
    private final static Integer STANDARD_LEN = 2040;

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
     * @param e       异常
     * @return 日志
     */
    public static SysLog exceptionErrorBuilder(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        SysLog sysLog = logBuilder(request);
        sysLog.setLevel(LogLevel.ERROR.ordinal());
        sysLog.setRemark(StringUtils.left(e.getMessage(), MAX_LEN));
        sysLog.setResult(StringUtils.left(printStackTraceToString(e), STANDARD_LEN));
        return sysLog;
    }

    /**
     * 警告日志封装
     *
     * @param request 请求
     * @param e       异常
     * @return 日志
     */
    public static SysLog exceptionWarnBuilder(HttpServletRequest request, Exception e) {
        SysLog sysLog = logBuilder(request);
        sysLog.setLevel(LogLevel.WARN.ordinal());
        sysLog.setResult(e.getMessage());
        return sysLog;
    }

    /**
     * 堆栈信息转化
     *
     * @param t 异常
     * @return 转化字符串
     */
    private static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
