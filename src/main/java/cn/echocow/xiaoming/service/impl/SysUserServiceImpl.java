package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.mapper.SysUserMapper;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:40
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser loadUser(String identity) {
        return Optional.ofNullable(sysUserMapper.findByIdentity(identity)).orElseThrow(() ->
                new UsernameNotFoundException("the user not exist!"));
    }

    @Override
    public SysUser loadUserByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        return Optional.ofNullable(sysUserMapper.findByUser(sysUser)).orElseThrow(() ->
                new UsernameNotFoundException("the user not exist!"));
    }

    @Override
    public SysUser loadUserByMobile(String phone) {
        SysUser sysUser = new SysUser();
        sysUser.setPhone(phone);
        return Optional.ofNullable(sysUserMapper.findByUser(sysUser)).orElseThrow(() ->
                new UsernameNotFoundException("the user not exist!"));
    }

    @Override
    public SysUser loadUserByEmail(String email) {
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);
        return Optional.ofNullable(sysUserMapper.findByUser(sysUser)).orElseThrow(() ->
                new UsernameNotFoundException("the user not exist!"));
    }

    @Override
    public String existUser(SysUser user) {
        if (user.getUsername() != null && sysUserMapper.findSimpleByIdentity(user.getUsername()) != null){
            return StringFormatter.format("user by %s already exist!", user.getUsername()).getValue();
        }
        if (user.getPhone() != null && sysUserMapper.findSimpleByIdentity(user.getPhone()) != null){
            return StringFormatter.format("user by %s already exist!", user.getPhone()).getValue();
        }
        if (user.getEmail() != null && sysUserMapper.findSimpleByIdentity(user.getEmail()) != null){
            return StringFormatter.format("user by %s already exist!", user.getEmail()).getValue();
        }
        return null;
    }

}
