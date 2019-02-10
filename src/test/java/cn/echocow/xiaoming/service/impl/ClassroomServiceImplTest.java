package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.XiaoMingApplication;
import cn.echocow.xiaoming.model.entity.Classroom;
import cn.echocow.xiaoming.service.ClassroomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiaoMingApplication.class)
public class ClassroomServiceImplTest {

    @Autowired private ClassroomService classroomService;

    @Test
    public void findAllTest() {
        List<Classroom> all = classroomService.findAll();
        assertNotNull(all);
        all.forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        Classroom classroom = new Classroom();
        classroom.setName("123123123123123");
        classroom.setNumber(101);
        Classroom update = classroomService.update(1L, classroom);
        assertNotNull(update);
        System.out.println(update);
    }

    @Test
    public void saveTest() {
        Classroom classroom = new Classroom();
        classroom.setName("aaaaa");
        classroom.setNumber(123);
        Classroom save = classroomService.save(classroom);
        assertNotNull(save);
    }

    @Test
    public void findByIdTest() {
        assertNotNull(classroomService.findById(1L));
    }


}