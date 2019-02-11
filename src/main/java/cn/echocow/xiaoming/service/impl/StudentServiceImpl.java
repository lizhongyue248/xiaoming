package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.SysUser;
import cn.echocow.xiaoming.repository.StudentRepository;
import cn.echocow.xiaoming.repository.SysUserRepository;
import cn.echocow.xiaoming.service.StudentService;
import cn.echocow.xiaoming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"student"}, keyGenerator = "cacheKeyGenerator")
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository> implements StudentService {

    private final StudentRepository studentRepository;
    private final SysUserRepository sysUserRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SysUserRepository sysUserRepository) {
        this.studentRepository = studentRepository;
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public Student findByUsername(String username) {
        SysUser user = sysUserRepository.findFirstByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the user by username %s not found!", username)));
        return studentRepository.findFirstByUser(user).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the student by username %s not found!", username)));
    }
}
