package cn.echocow.xiaoming.service;

import java.util.concurrent.TimeUnit;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 23:14
 */
public interface Oauth2Service {
    /**
     * 保存
     *
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param timeUnit 单位
     */
    void saveValidateCode(Object key, Object value, Long time, TimeUnit timeUnit);

    /**
     * 验证码验证
     *
     * @param key 键
     * @param code 验证码
     * @return 验证结果
     */
    boolean validate(Object key, Object code);
}
