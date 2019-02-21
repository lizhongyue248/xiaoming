package cn.echocow.xiaoming.base.impl;

import cn.echocow.xiaoming.base.BaseEntity;
import cn.echocow.xiaoming.base.BaseService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-17 20:39
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {


}
