package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.SysMenu;
import cn.echocow.xiaoming.entity.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * 测试 sys_user
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 21:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
@WebAppConfiguration
public class SysUserRepositoryTest {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserService sysUserService;
    /**
     * 测试查询所有
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public void testFindAll() {
//        List<SysUser> users = sysUserRepository.findAll();
//        users.forEach(user -> {
//            System.out.println(user);
//            user.getRoles().forEach(role -> {
//                System.out.println(role);
//                role.getMenus().forEach(System.out::println);
//            });
//        });
        SysUser admin = sysUserService.loadUserByUsername("admin");
        System.out.println(admin);
        admin.getRoles().forEach(sysRole -> {
            System.out.println(sysRole.toString());
            sysRole.getMenus().forEach(sysMenu -> {
                System.out.println("menu" + sysMenu);
                System.out.println(sysMenu.getRoles());
            });
        });
    }

    /**
     * 测试管理员用户
     */
    @Test
    public void testFindFirstByUsernameAndEnabledTrue() {
        Optional<SysUser> admin = sysUserRepository.findFirstByUsernameAndEnabledTrue("admin");
        assertNull(admin);
    }
}