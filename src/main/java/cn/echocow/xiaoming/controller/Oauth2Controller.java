package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.exception.*;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.model.helper.AccessToken;
import cn.echocow.xiaoming.model.properties.SecurityConfig;
import cn.echocow.xiaoming.model.properties.ValidateConfig;
import cn.echocow.xiaoming.resource.RestResource;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
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
    private final SysUserService sysUserService;
    private final TokenEnhancer tokenEnhancer;
    private final ValidateConfig validateConfig;
    private final SecurityConfig securityConfig;
    private final MailUtils mailUtils;
    private final DefaultTokenServices defaultTokenServices;
    private final Oauth2Service oauth2Service;
    private final TemplateEngine templateEngine;
    private final SubMailUtils subMailUtils;
    private final static String MOBILE_LOGIN = "MOBILE_LOGIN_";
    private final static String EMAIL_LOGIN = "EMAIL_LOGIN_";
    private final static String EMAIL_REGISTER = "EMAIL_REGISTER_";
    private final static String MOBILE_REGISTER = "MOBILE_REGISTER_";

    @Autowired
    public Oauth2Controller(ClientDetailsService clientDetailsService, SysUserService sysUserService,
                            @Qualifier("accessTokenConverter") TokenEnhancer tokenEnhancer, ValidateConfig validateConfig,
                            SecurityConfig securityConfig, MailUtils mailUtils, DefaultTokenServices defaultTokenServices,
                            Oauth2Service oauth2Service, TemplateEngine templateEngine, SubMailUtils subMailUtils) {
        this.clientDetailsService = clientDetailsService;
        this.sysUserService = sysUserService;
        this.tokenEnhancer = tokenEnhancer;
        this.validateConfig = validateConfig;
        this.securityConfig = securityConfig;
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
    @GetMapping("/login/email")
    public HttpEntity<?> loginEmailCode(@RequestParam String email) {
        if (StringUtils.isBlank(email)) {
            return ResponseEntity.badRequest().build();
        }
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);
        String result = sysUserService.existUser(sysUser);
        if (result == null) {
            throw new ResourceNoFoundException("user", "email", email);
        }
        return codeEmail(email, EMAIL_LOGIN, "loginTemplate.html", "[XIAOMING]欢迎登录");
    }

    /**
     * 邮箱登录，验证
     *
     * @param params 参数
     * @return http 响应
     */
    @PostMapping("/login/email")
    public HttpEntity<?> emailLogin(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String code = params.get("code");
        return StringUtils.isAnyBlank(email, code) ?
                ResponseEntity.badRequest().build() :
                createToken(EMAIL_LOGIN + email, code, sysUserService.loadUserByEmail(email));
    }

    /**
     * 手机号登录，获取验证码
     *
     * @param phone 手机号
     * @return http 响应
     */
    @GetMapping("/login/mobile/{phone}")
    public HttpEntity<?> loginMobileCode(@PathVariable String phone) {
        if (StringUtils.isBlank(phone) || phone.length() != 11) {
            return ResponseEntity.badRequest().build();
        }
        SysUser sysUser = new SysUser();
        sysUser.setPhone(phone);
        String result = sysUserService.existUser(sysUser);
        if (result == null) {
            throw new ResourceNoFoundException("user", "phone", phone);
        }
        return codeMobile(phone, MOBILE_LOGIN);
    }

    /**
     * 手机号验证
     *
     * @param params 验证参数
     * @return http 响应
     */
    @PostMapping("/login/mobile")
    public HttpEntity<?> mobileLogin(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String code = params.get("code");
        SysUser sysUser = sysUserService.loadUserByMobile(phone);
        return StringUtils.isAnyBlank(phone, code) ?
                ResponseEntity.badRequest().build() :
                createToken(MOBILE_LOGIN + phone, code, sysUser);
    }

    /***
     * 获取手机注册验证码
     *
     * @param phone 手机号
     * @return http 响应
     */
    @GetMapping("/register/mobile/{phone}")
    public HttpEntity<?> registerCodeMobile(@PathVariable("phone") String phone) {
        if (StringUtils.isBlank(phone) || phone.length() != 11) {
            return ResponseEntity.badRequest().build();
        }
        SysUser sysUser = new SysUser();
        sysUser.setPhone(phone);
        String result = sysUserService.existUser(sysUser);
        if (result != null) {
            throw new ResourceExistException(result);
        }
        return codeMobile(phone, MOBILE_REGISTER);
    }

    /***
     * 获取邮箱注册验证码
     *
     * @param email 邮箱号
     * @return http 响应
     */
    @GetMapping("/register/email")
    public HttpEntity<?> registerCodeEmail(@RequestParam String email) {
        if (StringUtils.isBlank(email)) {
            return ResponseEntity.badRequest().build();
        }
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);
        String result = sysUserService.existUser(sysUser);
        if (result != null) {
            throw new ResourceExistException(result);
        }
        return codeEmail(email, EMAIL_REGISTER, "registerTemplate.html", "[XIAOMING]欢迎注册");
    }

    /**
     * 注册，注册时，只需要手机验证码
     * 存在 remark 字段里
     *
     * @param entity        传递
     * @param bindingResult 参数校验
     * @return http 响应
     */
    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody SysUser entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        entity.setId(null);
        String existUser = sysUserService.existUser(entity);
        if (entity.getPassword() != null) {
            entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        }
        if (existUser != null) {
            throw new ResourceExistException(existUser);
        }
        if (StringUtils.isAnyBlank(entity.getPhone(), entity.getRemark())) {
            throw new BadRequestException("Please enter phone or phone！");
        }
        String code = entity.getRemark();
        entity.setRemark(null);
        if (!oauth2Service.validate(MOBILE_REGISTER + entity.getPhone(), code)) {
            throw new BadRequestException("mobile code error!");
        }
        if (!sysUserService.save(entity)) {
            throw new ServiceException("the resource save failed!");
        }
        return new ResponseEntity<>(new RestResource<>(entity, this.getClass()), HttpStatus.CREATED);

    }

    /**
     * 邮箱验证码生成
     *
     * @param email    邮箱
     * @param type     验证码类型
     * @param template 邮件模板
     * @param subject  主题
     * @return http 响应
     */
    private HttpEntity<?> codeEmail(String email, String type, String template, String subject) {
        String code = RandomStringUtils.randomNumeric(
                validateConfig.getEmailCodeMinLength(),
                validateConfig.getEmailCodeMaxLength());
        String key = type + email;
        Context context = new Context();
        context.setVariable("code", code);
        String content = templateEngine.process(template, context);
        mailUtils.sendTemplateMail("echocow@qq.com", email, subject, content);
        oauth2Service.saveValidateCode(key, code,
                validateConfig.getEmailCodeValidityPeriod(),
                TimeUnit.MINUTES);
        log.info("{} 邮箱验证码已经发送：{}", email, code);
        return ResponseEntity.ok().build();
    }


    /**
     * 手机验证码生成
     *
     * @param phone 手机号
     * @param type  验证码类型
     * @return http 响应
     */
    private HttpEntity<?> codeMobile(String phone, String type) {
        String key = type + phone;
        String code = RandomStringUtils.randomNumeric(
                validateConfig.getMobileCodeMinLength(),
                validateConfig.getMobileCodeMaxLength());
        oauth2Service.saveValidateCode(key, code,
                validateConfig.getMobileCodeValidityPeriod(),
                TimeUnit.MINUTES);
        JSONObject vars = new JSONObject();
        vars.put("code", code);
        vars.put("time", validateConfig.getMobileCodeValidityPeriod());
        if (!subMailUtils.sendRegisterMessage(phone, vars)) {
            throw new RuntimeException("message send failed!");
        }
        log.info("{} 短信验证码已经发送：{}", phone, code);
        return ResponseEntity.ok().build();
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
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(securityConfig.getClientId());
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
