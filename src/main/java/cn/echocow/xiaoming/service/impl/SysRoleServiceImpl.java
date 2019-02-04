package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.SysRole;
import cn.echocow.xiaoming.repository.SysRoleRepository;
import cn.echocow.xiaoming.service.SysRoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
@CacheConfig(cacheNames = {"sysRole"}, keyGenerator = "cacheKeyGenerator")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Long, SysRoleRepository> implements SysRoleService {
    @Resource
    private SysRoleRepository sysRoleRepository;

    @Override
    @Cacheable
    public List<SysRole> findAllByPermissionId(Long menuId) {
        return sysRoleRepository.findAllByPermissionId(menuId);
    }
}
