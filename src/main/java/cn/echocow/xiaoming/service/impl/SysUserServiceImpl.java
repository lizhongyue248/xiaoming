package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.repository.SysUserRepository;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:40
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser loadUserByUsername(String username) {
        return sysUserRepository.findFirstByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );
    }
}
