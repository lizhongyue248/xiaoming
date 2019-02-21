package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.model.entity.SysPermission;
import cn.echocow.xiaoming.mapper.SysPermissionMapper;
import cn.echocow.xiaoming.model.entity.SysRole;
import cn.echocow.xiaoming.service.SysPermissionService;
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
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> findAllByRoles(List<SysRole> roles) {
        List<Long> ids = new ArrayList<>();
        // 加入公共资源
        ids.add(1L);
        roles.forEach(role -> ids.add(role.getId()));
        return Optional.ofNullable(sysPermissionMapper.findAllByRoleIds(ids))
                .orElse(new ArrayList<>());
    }

}
