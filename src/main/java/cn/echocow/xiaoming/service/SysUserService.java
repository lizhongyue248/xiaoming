package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.repository.SysUserRepository;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:39
 */
public interface SysUserService extends BaseService<SysUser, Long, SysUserRepository> {
    /**
     * 通过用户名加载用户
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser loadUserByUsername(String username);

    /**
     * 通过用户名查找启用的用户的 ID
     *
     * @param username 用户名
     * @return 结果
     */
    Optional<Long> findFirstIdByUsernameAndEnabledTrue(String username);


}
