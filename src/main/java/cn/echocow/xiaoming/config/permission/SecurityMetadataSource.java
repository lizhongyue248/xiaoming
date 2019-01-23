package cn.echocow.xiaoming.config.permission;

import cn.echocow.xiaoming.entity.SysMenu;
import cn.echocow.xiaoming.entity.SysRole;
import cn.echocow.xiaoming.service.SysMenuService;
import cn.echocow.xiaoming.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 22:53
 */
@Component
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final SysMenuService sysMenuService;

    private final SysRoleService sysRoleService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    public SecurityMetadataSource(SysMenuService sysMenuService, SysRoleService sysRoleService) {
        this.sysMenuService = sysMenuService;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<SysMenu> menus = sysMenuService.findAll();
        for (SysMenu menu : menus) {
            if (!antPathMatcher.match(menu.getUrl(), requestUrl)) {
                continue;
            }
            List<SysRole> roles = sysRoleService.findAllByMenuId(menu.getId());
            if (roles == null || roles.isEmpty()) {
                continue;
            }
            List<String> values = roles.stream().map(SysRole::getName).collect(Collectors.toList());
            if (values.contains("ROLE_PUBLIC")) {
                return SecurityConfig.createList("ROLE_PUBLIC");
            }
            return SecurityConfig.createList(values.toArray(new String[values.size()]));
        }
        return SecurityConfig.createList("ROLE_NO_AUTH");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
