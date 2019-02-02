package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.repository.sys.SysLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-01 20:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
public class SysLogRepositoryTest {
    @Autowired
    private SysLogRepository sysLogRepository;

    @Test
    public void pageTest(){
        Page<SysLog> all = sysLogRepository.findAll(PageRequest.of(1, 10));
        all.forEach(System.out::println);
    }

}
