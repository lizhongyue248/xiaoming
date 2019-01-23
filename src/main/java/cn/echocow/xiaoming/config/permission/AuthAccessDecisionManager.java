package cn.echocow.xiaoming.config.permission;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 22:59
 */
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
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
