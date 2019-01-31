package cn.echocow.xiaoming.config;

import cn.echocow.xiaoming.entity.sys.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 20:57
 */
@Configuration
public class UserAuditorConfig implements AuditorAware<Long> {
    private final SysUserService sysUserService;

    @Autowired
    public UserAuditorConfig(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null ||
                context.getAuthentication().getPrincipal() == null){
            return Optional.empty();
        }
        Object principal = context.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(Long.class)) {
            return Optional.of((Long) principal);
        }
        SysUser sysUser = sysUserService.loadUserByUsername(principal.toString());
        if (sysUser != null){
            return Optional.of(sysUser.getId());
        }
        return Optional.empty();
    }
}
