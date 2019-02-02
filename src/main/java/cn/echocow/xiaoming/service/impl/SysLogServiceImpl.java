package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.sys.SysLogRepository;
import cn.echocow.xiaoming.service.SysLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<SysLog> findAll() {
        return sysLogRepository.findAll();
    }

    @Override
    public Page<SysLog> findAll(Pageable pageable) {
        return sysLogRepository.findAll(pageable);
    }

    @Override
    public SysLog findById(long id) {
        return sysLogRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_log by id %s not found!", id)));
    }
}
