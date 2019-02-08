package cn.echocow.xiaoming.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;

/**
 * 服务接口基类
 *
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
     * @return 集合
     */
    List<T> findAll();

    /**
     * 分页查询
     *
     * @param pageable 分页
     * @return 结果
     */
    Page<T> findAll(Pageable pageable);
}
