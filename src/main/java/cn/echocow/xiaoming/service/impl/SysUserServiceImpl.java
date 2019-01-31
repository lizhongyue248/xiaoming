package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.sys.SysUser;
import cn.echocow.xiaoming.exception.ResourceExistException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.sys.SysUserRepository;
import cn.echocow.xiaoming.service.SysUserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:40
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser loadUserByUsername(String username) {
        return sysUserRepository.findFirstByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new UsernameNotFoundException("用户不存在")
        );
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (!sysUserRepository.existsById(id)) {
            throw new ResourceNoFoundException(String.format("sys_user by id %s not found!", id));
        }
        sysUserRepository.deleteById(id);
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
    public SysUser put(Long id, SysUser sysUser) {
        sysUserRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", id)));
        sysUser.setId(id);
        return save(sysUser);
    }

    @Override
    public SysUser patch(Long id, SysUser sysUser) {
        SysUser exist = sysUserRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", id)));
        BeanWrapper beanWrapper = new BeanWrapperImpl(sysUser);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (beanWrapper.getPropertyValue(pd.getName()) == null) {
                nullPropertyNames.add(pd.getName());
            }
        }
        BeanUtils.copyProperties(sysUser, exist, nullPropertyNames.toArray(new String[nullPropertyNames.size()]));
        return save(sysUser);
    }


}
