package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.SysMenu;
import cn.echocow.xiaoming.entity.SysUser;
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

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<SysUser> users = sysUserRepository.findAll();
        users.forEach(user -> {
            System.out.println(user);
            user.getRoles().forEach(role -> {
                System.out.println(role);
                role.getMenus().forEach(System.out::println);
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