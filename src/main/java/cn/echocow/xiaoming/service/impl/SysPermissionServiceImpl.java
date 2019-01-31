package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.sys.SysPermission;
import cn.echocow.xiaoming.repository.sys.SysPermissionRepository;
import cn.echocow.xiaoming.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionRepository sysPermissionRepository;

    @Override
    public List<SysPermission> findAll() {
        return sysPermissionRepository.findAll();
    }
}
