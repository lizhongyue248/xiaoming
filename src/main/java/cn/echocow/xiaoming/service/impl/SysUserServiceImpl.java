package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.utils.CustomBeanUtils;
import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.SysUserRepository;
import cn.echocow.xiaoming.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:40
 */
@Service
@CacheConfig(cacheNames = {"sysUser"}, keyGenerator = "cacheKeyGenerator")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long, SysUserRepository> implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    @Cacheable
    public SysUser loadUser(String identity) {
        Optional<SysUser> sysUser = sysUserRepository.findFirstByUsernameAndEnabledTrue(identity);
        if (sysUser.isPresent()) {
            return sysUser.get();
        }
        sysUser = sysUserRepository.findFirstByEmailAndEnabledTrue(identity);
        if (sysUser.isPresent()) {
            return sysUser.get();
        }
        sysUser = sysUserRepository.findFirstByPhoneAndEnabledTrue(identity);
        if (sysUser.isPresent()) {
            return sysUser.get();
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    @Override
    @Cacheable
    public SysUser loadUserByUsername(String username) {
        return sysUserRepository.findFirstByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );
    }

    @Override
    @Cacheable
    public SysUser loadUserByMobile(String phone) {
        return sysUserRepository.findFirstByPhoneAndEnabledTrue(phone).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );
    }

    @Override
    @Cacheable
    public SysUser loadUserByEmail(String email) {
        return sysUserRepository.findFirstByEmailAndEnabledTrue(email).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在"));
    }

    @Override
    @Cacheable
    public Optional<Long> findFirstIdByUsernameAndEnabledTrue(String username) {
        return sysUserRepository.findFirstIdByUsernameAndEnabledTrue(username);
    }

    @Override
    @CacheEvict
    public SysUser save(SysUser sysUser) {
        String username = sysUser.getUsername();
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        if (sysUserRepository.findFirstByUsernameAndEnabledTrue(username).isPresent()) {
            throw new ResourceExistException(String.format("sys_user by username %s already exist!", username));
        }
        return sysUserRepository.save(sysUser);
    }

    @Override
    @CachePut
    public SysUser update(Long id, SysUser sysUser) {
        if (StringUtils.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        }
        SysUser exist = sysUserRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", id)));
        if (StringUtils.compare(sysUser.getUsername(), exist.getUsername()) != 0
                && sysUserRepository.findFirstIdByUsernameAndEnabledTrue(sysUser.getUsername()).isPresent()) {
            throw new ResourceExistException(String.format("sys_user by username %s already exist!", sysUser.getUsername()));
        }
        sysUser.getRoles().addAll(exist.getRoles());
        sysUser.setRoles(new ArrayList<>(new LinkedHashSet<>(sysUser.getRoles())));
        BeanUtils.copyProperties(sysUser, exist, CustomBeanUtils.getNullPropertyNames(sysUser));
        return exist;
    }

}
