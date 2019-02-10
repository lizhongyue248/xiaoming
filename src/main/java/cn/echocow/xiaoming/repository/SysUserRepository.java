package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.model.entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-22 22:52
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {
    /**
     * 通过用户名查找启用的用户
     *
     * @param username 用户名
     * @return 结果
     */
    Optional<SysUser> findFirstByUsernameAndEnabledTrue(@Param("username") String username);


    /**
     * 通过用户名查找启用的用户的 ID
     *
     * @param username 用户名
     * @return 结果
     */
    @Query("select distinct id from SysUser where username = :username and enabled = true ")
    Optional<Long> findFirstIdByUsernameAndEnabledTrue(@Param("username") String username);
}
