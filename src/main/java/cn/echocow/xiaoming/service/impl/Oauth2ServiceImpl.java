package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.service.Oauth2Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 23:17
 */
@Service
public class Oauth2ServiceImpl implements Oauth2Service {
    private final RedisTemplate redisTemplate;

    @Autowired
    public Oauth2ServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveValidateCode(Object key, Object value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public boolean validate(Object key, Object code) {
        Object valueObj = redisTemplate.opsForValue().get(key);
        boolean res = valueObj != null && StringUtils.equalsIgnoreCase(code.toString(), valueObj.toString());
        if (res){
            redisTemplate.delete(key);
        }
        return res;
    }
}
