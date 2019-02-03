package cn.echocow.xiaoming.base;

import cn.echocow.xiaoming.resource.RestResources;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;

import java.io.Serializable;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:25
 */
public interface BaseService<T extends BaseEntity, ID extends Serializable, R extends BaseRepository<T, ID>> {

    /**
     * 通过 id 更新实体
     *
     * @param id     id
     * @param entity 实体
     * @return 更新后的实体
     */
    T update(ID id, T entity);

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 保存后的实体
     */
    T save(T entity);

    /**
     * 通过 id 查询
     *
     * @param id id
     * @return 实体
     */
    T findById(ID id);

    /**
     * 通过 id 删除
     *
     * @param id id
     */
    void deleteById(ID id);

    /**
     * 判断是否存在指定 id 对象
     *
     * @param id id
     * @return 结果
     */
    boolean exists(ID id);

    /**
     * 查询所有
     *
     * @param clazz 控制器
     * @return 封装好的资源结果集
     */
    Resources<?> findAllResources(Class clazz);

    /**
     * 查询所有
     *
     * @return 集合
     */
    List<T> findAll();


    /**
     * 分页查询
     *
     * @param pageable 分页
     * @param clazz    控制器
     * @return 结果
     */
    RestResources<?> findAll(Pageable pageable, Class clazz);

}
