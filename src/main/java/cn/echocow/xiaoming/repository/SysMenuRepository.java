package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 22:50
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {
//    @Override
//    @Query("select m from SysMenu m, SysMenuRole mr, SysRole r where m.id = mr.menuId and mr.roleId = r.id")
//    List<SysMenu> findAll();
}
