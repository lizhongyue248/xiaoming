package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.entity.sys.SysLog;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 13:55
 */
public interface SysLogService {

    /**
     * 日志保存操作
     *
     * @param sysLog 保存的日志
     * @return 保存后的只
     */
    SysLog save(SysLog sysLog);
}
