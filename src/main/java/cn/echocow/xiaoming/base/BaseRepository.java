package cn.echocow.xiaoming.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 资源仓库基类
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 20:25
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 批量删除
     *
     * @param ids ids
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("delete from #{#entityName} e where e.id in (:ids)")
    void deleteBatch(@Param("ids")List<Long> ids);
}
