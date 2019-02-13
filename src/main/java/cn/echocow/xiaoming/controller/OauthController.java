package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import cn.echocow.xiaoming.service.SysUserService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 16:20
 */
@RestController
@RequestMapping("/auth")
public class OauthController {

    private final ClientDetailsService clientDetailsService;
    private final ApplicationProperties applicationProperties;
    private final RedisTemplate redisTemplate;
    private final SysUserService sysUserService;
    private final TokenEnhancer tokenEnhancer;
    @Autowired
    public OauthController(ClientDetailsService clientDetailsService, ApplicationProperties applicationProperties,
                           RedisTemplate redisTemplate, SysUserService sysUserService, TokenEnhancer tokenEnhancer) {
        this.clientDetailsService = clientDetailsService;
        this.applicationProperties = applicationProperties;
        this.redisTemplate = redisTemplate;
        this.sysUserService = sysUserService;
        this.tokenEnhancer = tokenEnhancer;
    }

    @GetMapping("/mobile/{phone}")
    public HttpEntity<?> mobileCode(@PathVariable String phone) {
        String key = phone + "mobile";
        Boolean has = redisTemplate.hasKey(key);
        assert has != null;
        if (has) {
            String value = Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
            redisTemplate.opsForValue().set(key, value, 60L, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, "123456", 60L, TimeUnit.SECONDS);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mobile")
    public HttpEntity<?> mobileAuth(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String code = params.get("code");
        if (StringUtils.isAnyBlank(phone, code)) {
            return ResponseEntity.badRequest().build();
        }
        String key = phone + "mobile";
        Boolean has = redisTemplate.hasKey(key);
        assert has != null;
        if (has) {
            String value = Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
            if (StringUtils.endsWithIgnoreCase(code, value)) {
                redisTemplate.delete(key);
                ClientDetails clientDetails = clientDetailsService.loadClientByClientId(applicationProperties.getSecurity().getClientId());
                TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0), clientDetails.getClientId(), clientDetails.getScope(), "mobile");
                OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
                SysUser sysUser = sysUserService.loadUserByMobile(phone);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(), sysUser.getAuthorities());
                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
                JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
                jwtAccessTokenConverter.setSigningKey("123");
                DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
                defaultTokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));
                OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);
                accessToken = tokenEnhancer.enhance(accessToken, oAuth2Authentication);
                return ResponseEntity.ok(new Gson().toJson(accessToken));
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
