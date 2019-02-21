package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.SysPermission;
import cn.echocow.xiaoming.model.entity.SysRole;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 21:13
 */
public interface SysPermissionService extends BaseService<SysPermission> {

    /**
     * 根据给定的角色查询当前用户所有的权限
     *
     * @param roles 角色
     * @return 权限
     */
    List<SysPermission> findAllByRoles(List<SysRole> roles);

}
