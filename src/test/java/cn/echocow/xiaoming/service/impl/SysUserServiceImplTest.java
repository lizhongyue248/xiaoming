package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.XiaoMingApplication;
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

import java.util.Optional;

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
    public void putTest() {
//        SysUser sysUser = sysUserRepository.findById(2L).orElseThrow(() ->
//                new ResourceNoFoundException(String.format("sys_user by id %s not found!", 2)));
//        sysUser.setUsername("admin2");
//        sysUserRepository.save(sysUser);
        Optional<Long> admin = sysUserRepository.findFirstIdByUsernameAndEnabledTrue("admin");
        assert admin.isPresent();
        Long aLong = admin.get();
        System.out.println(aLong);
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