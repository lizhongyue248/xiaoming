package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.repository.StudentRepository;
import cn.echocow.xiaoming.service.StudentService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"student"}, keyGenerator = "cacheKeyGenerator")
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository> implements StudentService {
}
