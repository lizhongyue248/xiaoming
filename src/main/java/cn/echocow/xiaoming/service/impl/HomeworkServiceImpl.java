package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.base.impl.BaseServiceImpl;
import cn.echocow.xiaoming.entity.Homework;
import cn.echocow.xiaoming.repository.HomeworkRepository;
import cn.echocow.xiaoming.service.HomeworkService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:33
 */
@Service
@CacheConfig(cacheNames = {"homework"}, keyGenerator = "cacheKeyGenerator")
public class HomeworkServiceImpl extends BaseServiceImpl<Homework, Long, HomeworkRepository> implements HomeworkService {
}
