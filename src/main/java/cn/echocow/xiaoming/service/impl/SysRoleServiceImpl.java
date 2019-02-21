package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.model.entity.SysRole;
import cn.echocow.xiaoming.mapper.SysRoleMapper;
import cn.echocow.xiaoming.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findAllByPermissionId(Long permissionId) {
        return Optional.ofNullable(sysRoleMapper.findByPermissionId(permissionId))
                .orElse(new ArrayList<>());
    }
}
