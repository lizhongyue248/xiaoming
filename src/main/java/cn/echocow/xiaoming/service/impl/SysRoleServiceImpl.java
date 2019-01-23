package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.SysRole;
import cn.echocow.xiaoming.repository.SysRoleRepository;
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
    public List<SysRole> findAllByMenuId(Long menuId) {
        return sysRoleRepository.findAllByMenuId(menuId);
    }
}
