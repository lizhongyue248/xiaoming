package cn.echocow.xiaoming.base;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.entity.Classroom;
import cn.echocow.xiaoming.resource.RestResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 22:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
public class RestResourceTest {

    @Test
    public void createTest(){
        RestResource<Classroom> resource = new RestResource<>(new Classroom(), BaseEntity.class);
        System.out.println(resource);
    }
}