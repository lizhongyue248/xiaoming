package cn.echocow.xiaoming.repository.sys;

import cn.echocow.xiaoming.entity.sys.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 菜单资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 22:50
 */
@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long> {

}
