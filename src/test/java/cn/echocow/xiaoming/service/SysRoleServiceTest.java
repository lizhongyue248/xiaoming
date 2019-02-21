package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.model.entity.SysRole;
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
 * @date 2019-02-21 11:29
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void findAllByPermissionId() {
        List<SysRole> roles = sysRoleService.findAllByPermissionId(2L);
        roles.forEach(role -> log.info(role.toString()));
    }
}