package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.model.entity.SysLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void deleteBatchTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        sysLogRepository.deleteBatch(ids);
    }
}
