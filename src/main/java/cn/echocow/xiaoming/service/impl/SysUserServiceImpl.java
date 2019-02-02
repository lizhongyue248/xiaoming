package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.SysUserRepository;
import cn.echocow.xiaoming.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:40
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long, SysUserRepository> implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser loadUserByUsername(String username) {
        return sysUserRepository.findFirstByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );
    }

    @Override
    public Optional<Long> findFirstIdByUsernameAndEnabledTrue(String username) {
        return sysUserRepository.findFirstIdByUsernameAndEnabledTrue(username);
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", id)));
    }

    @Override
    public SysUser save(SysUser sysUser) {
        String username = sysUser.getUsername();
        if (sysUserRepository.findFirstByUsernameAndEnabledTrue(username).isPresent()) {
            throw new ResourceExistException(String.format("sys_user by username %s already exist!", username));
        }
        return sysUserRepository.save(sysUser);
    }

    @Override
    public SysUser update(Long id, SysUser sysUser) {
        SysUser exist = sysUserRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", id)));
        if (StringUtils.compare(sysUser.getUsername(), exist.getUsername()) != 0
                && sysUserRepository.findFirstIdByUsernameAndEnabledTrue(sysUser.getUsername()).isPresent()) {
            throw new ResourceExistException(String.format("sys_user by username %s already exist!", sysUser.getUsername()));
        }
        BeanWrapper beanWrapper = new BeanWrapperImpl(sysUser);
        BeanUtils.copyProperties(sysUser, exist, Arrays.stream(beanWrapper.getPropertyDescriptors())
                .filter(propertyDescriptor -> beanWrapper.getPropertyValue(propertyDescriptor.getName()) == null)
                .map(FeatureDescriptor::getName).toArray(String[]::new));
        return exist;
    }
}
