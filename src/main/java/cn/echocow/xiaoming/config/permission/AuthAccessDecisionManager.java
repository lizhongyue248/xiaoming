package cn.echocow.xiaoming.config.permission;

import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 角色认证
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 22:59
 */
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {

    private final ApplicationProperties applicationProperties;

    private static final String ADMIN = "ROLE_ADMIN";

    @Autowired
    public AuthAccessDecisionManager(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 管理员模式：如果当前用户是管理员权限，放行所有请求
        if (applicationProperties.getAdmin() && authentication.getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))) {
            return;
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();
            if ("ROLE_NO_AUTH".equals(needRole)) {
                throw new AccessDeniedException("权限不足");
            }
            if ("ROLE_PUBLIC".equals(needRole) || authentication.getAuthorities().stream().anyMatch(
                    authority -> authority.getAuthority().equals(needRole))) {
                return;
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
