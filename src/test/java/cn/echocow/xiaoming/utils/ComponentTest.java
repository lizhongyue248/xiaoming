package cn.echocow.xiaoming.utils;

import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.model.properties.MessageConfig;
import cn.echocow.xiaoming.service.SysUserService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-22 12:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComponentTest {

    @Autowired
    private SubMailUtils subMailUtils;
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void sendManyTest() {
        List<SysUser> users = sysUserService.list();
        JSONObject vars = new JSONObject();
        vars.put("h", "作业名");
        vars.put("t", "3天");
        subMailUtils.sendHomeworkTipMessage(users, vars);
    }
}
