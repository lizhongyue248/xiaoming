package cn.echocow.xiaoming.base.impl;

import cn.echocow.xiaoming.utils.CustomBeanUtils;
import cn.echocow.xiaoming.base.BaseEntity;
import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 服务实现基类
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:26
 */
@CacheConfig(cacheNames = {"baseService"}, keyGenerator = "cacheKeyGenerator")
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable, R extends BaseRepository<T, ID>> implements BaseService<T, ID, R> {

    @Autowired
    protected R baseRepository;

    @Override
    @CachePut
    public T update(ID id, T entity) {
        T exist = baseRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the resource by id %s not found!", id)));
        BeanUtils.copyProperties(entity, exist, CustomBeanUtils.getNullPropertyNames(entity));
        return exist;
    }

    @Override
    @CacheEvict
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    @Cacheable
    public T findById(ID id) {
        return baseRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the resource by id %s not found!", id))
        );
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(ID id) {
        if (!baseRepository.existsById(id)) {
            throw new ResourceNoFoundException(String.format("the resource by id %s not found!", id));
        }
        baseRepository.deleteById(id);
    }

    @Override
    @Cacheable
    public boolean exists(ID id) {
        return baseRepository.existsById(id);
    }

    @Override
    @Cacheable
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteBatch(List<Long> ids) {
        Assert.notNull(ids, "ids can not is null!");
        baseRepository.deleteBatch(ids);
    }
}
