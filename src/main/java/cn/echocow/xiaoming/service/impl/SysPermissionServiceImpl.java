package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.SysPermission;
import cn.echocow.xiaoming.repository.SysPermissionRepository;
import cn.echocow.xiaoming.service.SysPermissionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
@CacheConfig(cacheNames = {"sysPermission"}, keyGenerator = "cacheKeyGenerator")
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, Long, SysPermissionRepository> implements SysPermissionService {

}
