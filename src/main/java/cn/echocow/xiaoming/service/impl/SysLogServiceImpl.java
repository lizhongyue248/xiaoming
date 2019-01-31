package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.repository.sys.SysLogRepository;
import cn.echocow.xiaoming.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 13:55
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogRepository sysLogRepository;

    @Override
    public SysLog save(SysLog sysLog) {
        return sysLogRepository.save(sysLog);
    }
}
