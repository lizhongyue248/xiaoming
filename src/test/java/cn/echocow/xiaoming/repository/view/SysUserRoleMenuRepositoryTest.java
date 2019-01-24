package cn.echocow.xiaoming.repository.view;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.view.SysUserRoleMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-24 20:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
@WebAppConfiguration
public class SysUserRoleMenuRepositoryTest {
    @Autowired
    private SysUserRoleMenuRepository sysUserRoleMenuRepository;

    @Test
    public void test() {
        List<SysUserRoleMenu> all = sysUserRoleMenuRepository.findAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
        all.forEach(System.out::println);
    }
}