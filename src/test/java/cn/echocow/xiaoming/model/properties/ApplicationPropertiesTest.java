package cn.echocow.xiaoming.model.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationPropertiesTest {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void test(){
        QiniuConfig qiniu = applicationProperties.getQiniu();
        assertNotNull(qiniu);
        System.out.println(qiniu.getAccessKey());
        System.out.println(qiniu.getArea());
        System.out.println(qiniu.getBucketName());
        System.out.println(applicationProperties.getName());
    }
}