package cn.echocow.xiaoming.repository.sys;

import cn.echocow.xiaoming.entity.sys.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * 通过菜单 id 查询所有关联的角色
     *
     * @param permissionId 菜单 id
     * @return 结果
     */
    @Query("select distinct r from SysRole r, SysPermission p, SysPermissionRole pr where pr.permissionId = :permissionId and pr.roleId = r.id")
    List<SysRole> findAllByPermissionId(@Param("permissionId") Long permissionId);

}
