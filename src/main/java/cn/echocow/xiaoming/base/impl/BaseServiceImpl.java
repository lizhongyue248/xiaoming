package cn.echocow.xiaoming.base.impl;

import cn.echocow.xiaoming.Utils.CustomBeanUtils;
import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.base.BaseService;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:26
 */
public abstract class BaseServiceImpl<T, ID extends Serializable, R extends BaseRepository<T, ID>> implements BaseService<T, ID, R> {

    @Autowired
    protected R baseRepository;

    @Override
    public T update(ID id, T entity) {
        T exist = baseRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the resource by id %s not found!", id)));
        BeanUtils.copyProperties(entity, exist, CustomBeanUtils.getNullPropertyNames(entity));
        return baseRepository.saveAndFlush(exist);
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return baseRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException(String.format("the resource by id %s not found!", id))
        );
    }

    @Override
    public void deleteById(ID id) {
        if (!baseRepository.existsById(id)) {
            throw new ResourceNoFoundException(String.format("the resource by id %s not found!", id));
        }
        baseRepository.deleteById(id);
    }

    @Override
    public boolean exists(ID id) {
        return baseRepository.existsById(id);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

}
