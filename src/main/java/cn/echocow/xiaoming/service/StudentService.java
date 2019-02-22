package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.Classroom;
import cn.echocow.xiaoming.model.entity.Student;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface StudentService extends BaseService<Student> {

    /**
     * 通过用户查询学生
     *
     * @param username 用户名称
     * @return 学生
     */
    Student findByUsername(String username);

    /**
     * 通过 classroom 查询所有学生
     *
     * @param classroom 班级
     * @return 学生
     */
    List<Student> findByClassroom(Classroom classroom);
}
