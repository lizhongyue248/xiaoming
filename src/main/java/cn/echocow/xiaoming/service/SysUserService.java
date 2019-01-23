package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.entity.SysUser;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:39
 */
public interface SysUserService {
    /**
     * 通过用户名加载用户
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser loadUserByUsername(String username);
}
