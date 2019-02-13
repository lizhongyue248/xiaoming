package cn.echocow.xiaoming.config.oauth2;

import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

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

    @Autowired
    public ResourceServerConfig(DefaultTokenServices defaultTokenServices, ApplicationProperties applicationProperties) {
        this.defaultTokenServices = defaultTokenServices;
        this.applicationProperties = applicationProperties;
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

}
