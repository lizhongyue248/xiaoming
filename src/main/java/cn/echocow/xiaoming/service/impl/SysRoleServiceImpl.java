package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.sys.SysRole;
import cn.echocow.xiaoming.repository.sys.SysRoleRepository;
import cn.echocow.xiaoming.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleRepository sysRoleRepository;

    @Override
    public List<SysRole> findAllByPermissionId(Long menuId) {
        return sysRoleRepository.findAllByPermissionId(menuId);
    }
}
