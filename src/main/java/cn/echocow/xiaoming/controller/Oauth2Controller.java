package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.model.helper.AccessToken;
import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import cn.echocow.xiaoming.service.Oauth2Service;
import cn.echocow.xiaoming.service.SysUserService;
import cn.echocow.xiaoming.utils.MailUtils;
import cn.echocow.xiaoming.utils.SubMailUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 16:20
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class Oauth2Controller {

    private final ClientDetailsService clientDetailsService;
    private final ApplicationProperties applicationProperties;
    private final SysUserService sysUserService;
    private final TokenEnhancer tokenEnhancer;
    private final MailUtils mailUtils;
    private final DefaultTokenServices defaultTokenServices;
    private final Oauth2Service oauth2Service;
    private final TemplateEngine templateEngine;
    private final SubMailUtils subMailUtils;
    private final static String MOBILE = "MOBILE";
    private final static String EMAIL = "EMAIL";

    @Autowired
    public Oauth2Controller(ClientDetailsService clientDetailsService, ApplicationProperties applicationProperties,
                            SysUserService sysUserService, @Qualifier("accessTokenConverter") TokenEnhancer tokenEnhancer, MailUtils mailUtils, DefaultTokenServices defaultTokenServices,
                            Oauth2Service oauth2Service, TemplateEngine templateEngine, SubMailUtils subMailUtils) {
        this.clientDetailsService = clientDetailsService;
        this.applicationProperties = applicationProperties;
        this.sysUserService = sysUserService;
        this.tokenEnhancer = tokenEnhancer;
        this.mailUtils = mailUtils;
        this.defaultTokenServices = defaultTokenServices;
        this.oauth2Service = oauth2Service;
        this.templateEngine = templateEngine;
        this.subMailUtils = subMailUtils;
    }

    /**
     * 邮箱登录，获取验证码
     *
     * @param email 邮箱
     * @return http 响应
     */
    @GetMapping("/email")
    public HttpEntity<?> emailCode(@RequestParam String email) {
        if (StringUtils.isBlank(email)) {
            return ResponseEntity.badRequest().build();
        }
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);
        String result = sysUserService.existUser(sysUser);
        if (result != null) {
            throw new ResourceExistException(result);
        }
        String code = RandomStringUtils.randomNumeric(
                applicationProperties.getValidate().getEmailCodeMinLength(),
                applicationProperties.getValidate().getEmailCodeMaxLength());
        String key = EMAIL + email;
        Context context = new Context();
        context.setVariable("code", code);
        String content = templateEngine.process("registerMailTemplate.html", context);
        mailUtils.sendTemplateMail("echocow@qq.com", email, "欢迎注册小明同学～！", content);
        oauth2Service.saveValidateCode(key, code,
                applicationProperties.getValidate().getEmailCodeValidityPeriod(),
                TimeUnit.MINUTES);
        log.info("{} 邮箱验证码已经发送：{}", email, code);
        return ResponseEntity.ok().build();
    }

    /**
     * 邮箱登录，验证
     *
     * @param params 参数
     * @return http 响应
     */
    @PostMapping("/email")
    public HttpEntity<?> emailAuth(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String code = params.get("code");
        return StringUtils.isAnyBlank(email, code) ?
                ResponseEntity.badRequest().build() :
                createToken(EMAIL + email, code, sysUserService.loadUserByEmail(email));
    }

    /**
     * 手机号登录，获取验证码
     *
     * @param phone 手机号
     * @return http 响应
     */
    @GetMapping("/mobile/{phone}")
    public HttpEntity<?> mobileCode(@PathVariable String phone) {
        SysUser sysUser = new SysUser();
        sysUser.setPhone(phone);
        String result = sysUserService.existUser(sysUser);
        if (result != null) {
            throw new ResourceExistException(result);
        }
        String key = MOBILE + phone;
        String code = RandomStringUtils.randomNumeric(
                applicationProperties.getValidate().getMobileCodeMinLength(),
                applicationProperties.getValidate().getMobileCodeMaxLength());
        oauth2Service.saveValidateCode(key, code,
                applicationProperties.getValidate().getMobileCodeValidityPeriod(),
                TimeUnit.MINUTES);
        JSONObject vars = new JSONObject();
        vars.put("code", code);
        vars.put("time", applicationProperties.getValidate().getMobileCodeValidityPeriod());
        if (!subMailUtils.sendRegisterMessage(phone, vars)) {
            throw new RuntimeException("message send failed!");
        }
        log.info("{} 短信验证码已经发送：{}", phone, code);
        return ResponseEntity.ok().build();
    }

    /**
     * 手机号验证
     *
     * @param params 验证参数
     * @return http 响应
     */
    @PostMapping("/mobile")
    public HttpEntity<?> mobileAuth(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String code = params.get("code");
        SysUser sysUser = sysUserService.loadUserByMobile(phone);
        return StringUtils.isAnyBlank(phone, code) ?
                ResponseEntity.badRequest().build() :
                createToken(MOBILE + phone, code, sysUser);
    }

    /**
     * 生成 token
     *
     * @param key     验证码的 key
     * @param code    验证码
     * @param sysUser 用户
     * @return http 响应
     */
    private HttpEntity<?> createToken(String key, String code, SysUser sysUser) {
        boolean validate = oauth2Service.validate(key, code);
        if (validate) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(applicationProperties.getSecurity().getClientId());
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0), clientDetails.getClientId(), clientDetails.getScope(), "mobile");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(), sysUser.getAuthorities());
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
            OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);
            accessToken = tokenEnhancer.enhance(accessToken, oAuth2Authentication);
            AccessToken result = new AccessToken(accessToken.getValue(), accessToken.getTokenType(), accessToken.getRefreshToken().getValue(),
                    accessToken.getExpiresIn(), accessToken.getScope(), accessToken.getAdditionalInformation().get("jti").toString());
            return ResponseEntity.ok(result);
        }
        throw new BadCredentialsException("Bad Credentials！");
    }

}
