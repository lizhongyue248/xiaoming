package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.repository.SysUserRepository;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:39
 */
public interface SysUserService extends BaseService<SysUser, Long, SysUserRepository> {

    /**
     * 通过身份查找用户，查询顺序如下
     * 1. 用户名
     * 2. 邮箱
     * 3. 手机号
     *
     * @param identity 身份
     * @return 结果
     */
    SysUser loadUser(String identity);

    /**
     * 通过用户名加载用户
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser loadUserByUsername(String username);

    /**
     * 通过手机号加载用户
     *
     * @param phone 手机号
     * @return 结果
     */
    SysUser loadUserByMobile(String phone);

    /**
     * 通过邮箱加载用户
     *
     * @param email 邮箱
     * @return 结果
     */
    SysUser loadUserByEmail(String email);
    /**
     * 通过用户名查找启用的用户的 ID
     *
     * @param username 用户名
     * @return 结果
     */
    Optional<Long> findFirstIdByUsernameAndEnabledTrue(String username);


}
