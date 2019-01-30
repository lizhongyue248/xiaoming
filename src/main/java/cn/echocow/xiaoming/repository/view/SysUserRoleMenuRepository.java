package cn.echocow.xiaoming.repository.view;

import cn.echocow.xiaoming.entity.view.SysUserRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-24 20:15
 */
@Repository
public interface SysUserRoleMenuRepository extends JpaRepository<SysUserRoleMenu, Long> {
}
