package cn.echocow.xiaoming.config.permission;

import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return new UserDetails() {
            SysUser sysUser = sysUserService.loadUser(username);

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (sysUser.getRoles() != null) {
                    sysUser.getRoles().forEach(sysRole -> authorities.add(new SimpleGrantedAuthority(sysRole.getName())));
                }
                return authorities;
            }

            @Override
            public String getPassword() {
                return sysUser.getPassword();
            }

            @Override
            public String getUsername() {
                return sysUser.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return sysUser.getEnabled();
            }
        };
    }
}
