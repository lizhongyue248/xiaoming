package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.repository.StudentRepository;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
public interface StudentService extends BaseService<Student, Long, StudentRepository> {

    /**
     * 通过用户查询学生
     *
     * @param username 用户名称
     * @return 学生
     */
    Student findByUsername(String username);
}
