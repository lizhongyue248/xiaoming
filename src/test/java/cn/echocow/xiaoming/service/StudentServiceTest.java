package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.mapper.StudentMapper;
import cn.echocow.xiaoming.model.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 21:58
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentServiceTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void findByUsernameTest() {
//        Student admin = studentService.findByUsernameAndEnabledTrue("admin");
//        assertNotNull(admin);
//        log.info(admin.toString());
        Student student = studentMapper.findFirstByUserId(1L);
        System.out.println(student);
    }
}