package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.model.entity.SysPermission;
import org.springframework.stereotype.Repository;


/**
 * 菜单资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 22:50
 */
@Repository
public interface SysPermissionRepository extends BaseRepository<SysPermission, Long> {

}
