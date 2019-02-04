package cn.echocow.xiaoming.config.permission;

import cn.echocow.xiaoming.entity.enums.HttpMethod;
import cn.echocow.xiaoming.entity.SysPermission;
import cn.echocow.xiaoming.entity.SysRole;
import cn.echocow.xiaoming.service.SysPermissionService;
import cn.echocow.xiaoming.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户权限匹配
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-21 22:53
 */
@Component
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final SysPermissionService sysPermissionService;

    private final SysRoleService sysRoleService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    public SecurityMetadataSource(SysPermissionService sysPermissionService, SysRoleService sysRoleService) {
        this.sysPermissionService = sysPermissionService;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        String method = httpRequest.getMethod();
        String requestUrl = httpRequest.getServletPath();
        List<SysPermission> permissions = sysPermissionService.findAll();
        for (SysPermission permission : permissions) {
            // 路径匹配
            if (!antPathMatcher.match(permission.getMatchUrl(), requestUrl)) {
                continue;
            }
            // 方法匹配
            if (!HttpMethod.ALL.match(permission.getMethod()) && !method.equals(permission.getMethod())) {
                continue;
            }
            // 角色匹配
            List<SysRole> roles = sysRoleService.findAllByPermissionId(permission.getId());
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
