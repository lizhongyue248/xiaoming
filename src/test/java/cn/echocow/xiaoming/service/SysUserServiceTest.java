package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.model.entity.SysPermission;
import cn.echocow.xiaoming.model.entity.SysRole;
import cn.echocow.xiaoming.model.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 21:44
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserServiceTest {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Test
    public void findTest() {
        SysUser user = sysUserService.getById(1L);
        assertNotNull(user);
        log.info(user.toString());
        log.info(user.getCreateTime().toString());
        log.info(user.getModifyTime().toString());
    }

    @Test
    public void loadUser() {
        SysUser admin = sysUserService.loadUser("123456");
        assertNotNull(admin);
        System.out.println(admin);
        List<SysRole> roles = admin.getRoles();
        roles.forEach(System.out::println);
        List<SysPermission> sysPermissions = sysPermissionService.findAllByRoles(roles);
        sysPermissions.forEach(System.out::println);
    }

    @Test
    public void loadUserByUsername() {
        SysUser admin = sysUserService.loadUserByUsername("admin");
        assertNotNull(admin);
        System.out.println(admin);
        List<SysRole> roles = admin.getRoles();
        roles.forEach(System.out::println);
        List<SysPermission> sysPermissions = sysPermissionService.findAllByRoles(roles);
        sysPermissions.forEach(System.out::println);
    }

    @Test
    public void loadUserByMobile() {
        SysUser admin = sysUserService.loadUserByMobile("123456");
        assertNotNull(admin);
        System.out.println(admin);
        List<SysRole> roles = admin.getRoles();
        roles.forEach(System.out::println);
        List<SysPermission> sysPermissions = sysPermissionService.findAllByRoles(roles);
        sysPermissions.forEach(System.out::println);
    }

    @Test
    public void loadUserByEmail() {
        SysUser admin = sysUserService.loadUserByEmail("lizhongyue248@163.com");
        assertNotNull(admin);
        System.out.println(admin);
        List<SysRole> roles = admin.getRoles();
        roles.forEach(System.out::println);
        List<SysPermission> sysPermissions = sysPermissionService.findAllByRoles(roles);
        sysPermissions.forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        SysUser admin = sysUserService.loadUser("admin");
        admin.setRemark("123123");
        assertTrue(sysUserService.updateById(admin));
    }
}