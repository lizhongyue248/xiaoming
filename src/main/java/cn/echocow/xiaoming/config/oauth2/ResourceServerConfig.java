package cn.echocow.xiaoming.config.oauth2;

import cn.echocow.xiaoming.config.permission.AuthAccessDecisionManager;
import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * oauth2 资源服务器
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-20 22:16
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final DefaultTokenServices defaultTokenServices;
    private final ApplicationProperties applicationProperties;
    private final FilterInvocationSecurityMetadataSource securityMetadataSource;
    private final AuthAccessDecisionManager authAccessDecisionManager;
    @Autowired
    public ResourceServerConfig(DefaultTokenServices defaultTokenServices, ApplicationProperties applicationProperties, FilterInvocationSecurityMetadataSource securityMetadataSource, AuthAccessDecisionManager authAccessDecisionManager) {
        this.defaultTokenServices = defaultTokenServices;
        this.applicationProperties = applicationProperties;
        this.securityMetadataSource = securityMetadataSource;
        this.authAccessDecisionManager = authAccessDecisionManager;
    }

    /**
     * 资源配置
     *
     * @param resources resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(applicationProperties.getSecurity().getResourceId()).stateless(true)
                .tokenServices(defaultTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(securityMetadataSource);
                        o.setAccessDecisionManager(authAccessDecisionManager);
                        return o;
                    }
                })
                .anyRequest().authenticated()
                .antMatchers("/auth/**/**").permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }
}
