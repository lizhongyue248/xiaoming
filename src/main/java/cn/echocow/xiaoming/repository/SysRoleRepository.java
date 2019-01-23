package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.entity.SysRole;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * 角色资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 22:51
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
    /**
     * 通过 id 查询启用的所有角色
     *
     * @param id id
     * @return 结果
     */
    List<SysRole> findAllByIdAndEnabledTrue(@Param("id") Long id);

    /**
     * 通过 id 集合查询并排序
     *
     * @param ids id
     * @param sort 排序
     * @return
     */
    List<SysRole> findAllByIdInAndEnabledTrue(Collection<Long> ids, Sort sort);

    /**
     * 通过菜单 id 查询所有关联已经启用的角色
     *
     * @param menuId 菜单 id
     * @return 结果
     */
    @Query("select distinct r from SysRole r, SysMenu m, SysMenuRole mr where mr.menuId = :menuId and mr.roleId = r.id and r.enabled = true ")
    List<SysRole> findAllByMenuId(@Param("menuId") Long menuId);

}
