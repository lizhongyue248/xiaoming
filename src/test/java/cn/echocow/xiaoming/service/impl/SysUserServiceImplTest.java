package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.SysPermission;
import cn.echocow.xiaoming.entity.SysRole;
import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.repository.SysUserRepository;
import cn.echocow.xiaoming.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-01 15:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
@WebAppConfiguration
public class SysUserServiceImplTest {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void findAllTest() {
        List<SysUser> all = sysUserRepository.findAll();
        assertNotNull(all);
        List<SysRole> roles = all.get(0).getRoles();
        for (SysRole role : roles) {
            List<SysPermission> permissions = role.getPermissions();
            permissions.forEach(System.out::println);
        }
    }

    @Test
    public void putServiceTest() {
        SysUser sysUser = sysUserRepository.findById(2L).orElseThrow(() ->
                new ResourceNoFoundException(String.format("sys_user by id %s not found!", 2)));
        sysUser.setUsername("admin3");
        SysUser put = sysUserService.update(2L, sysUser);
        System.out.println(put);
    }
}