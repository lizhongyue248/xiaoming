package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.SysRole;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 21:14
 */
public interface SysRoleService extends BaseService<SysRole> {
    /**
     * 通过权限 id 关联查询查询角色
     *
     * @param menuId 菜单id
     * @return 结果
     */
    List<SysRole> findAllByPermissionId(Long menuId);
}
