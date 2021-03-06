package cn.echocow.xiaoming.config.oauth2;

import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.time.Duration;

/**
 * oauth2 授权服务器
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-20 21:57
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserDetailsService userDetailsService;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter accessTokenConverter;
    private final ApplicationProperties applicationProperties;


    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager, RedisConnectionFactory redisConnectionFactory, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                     TokenStore tokenStore, JwtAccessTokenConverter accessTokenConverter, ApplicationProperties applicationProperties) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.applicationProperties = applicationProperties;
    }

    /**
     * 授权服务器配置
     *
     * @param security security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 客户端配置
     *
     * @param clients 客户端
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(applicationProperties.getSecurity().getClientId())
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(6000)
                .resourceIds(applicationProperties.getSecurity().getResourceId())
                .scopes("all")
                .accessTokenValiditySeconds((int) Duration.ofDays(1).getSeconds())
                .refreshTokenValiditySeconds((int) Duration.ofDays(1).getSeconds())
                .secret(applicationProperties.getSecurity().getClientSecret());
    }

    /**
     * 授权服务器端点配置
     *
     * @param endpoints 端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
