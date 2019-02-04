package cn.echocow.xiaoming.config.permission;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户登录认证,角色授权
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 22:23
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;

    @Autowired
    public UserDetailsServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserService.loadUserByUsername(username);
    }
}
