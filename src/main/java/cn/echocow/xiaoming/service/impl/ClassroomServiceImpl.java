package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.Classroom;
import cn.echocow.xiaoming.repository.ClassroomRepository;
import cn.echocow.xiaoming.service.ClassroomService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"classroom"}, keyGenerator = "cacheKeyGenerator")
public class ClassroomServiceImpl extends BaseServiceImpl<Classroom, Long, ClassroomRepository> implements ClassroomService {
}
