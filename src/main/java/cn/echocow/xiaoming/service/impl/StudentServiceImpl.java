package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.mapper.SysUserMapper;
import cn.echocow.xiaoming.model.entity.Classroom;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.mapper.StudentMapper;
import cn.echocow.xiaoming.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public Student findByUsername(String username) {
        SysUser sysUser = Optional.ofNullable(
                sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                        .lambda()
                        .eq(SysUser::getUsername, username)
                        .eq(SysUser::getEnabled, true)))
                .orElseThrow(() -> new ResourceNoFoundException(String.format("the user by username %s not found!", username)));
        return Optional.ofNullable(studentMapper.selectById(sysUser.getId())).orElseThrow(() ->
                new ResourceNoFoundException("student","username", username));
    }

    @Override
    public List<Student> findByClassroom(Classroom classroom) {
        return Optional.ofNullable(studentMapper.selectList(new QueryWrapper<Student>()
                .lambda()
                .eq(Student::getClassroomId, classroom.getId())))
                .orElse(Collections.emptyList());
    }
}
