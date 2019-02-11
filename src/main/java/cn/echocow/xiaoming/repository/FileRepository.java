package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.model.entity.File;
import cn.echocow.xiaoming.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 文件上传
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface FileRepository extends BaseRepository<File, Long> {

    /**
     * 通过 task 分页查询
     *
     * @param task task
     * @return 分页结果
     */
    Page<File> findByTask(Task task, Pageable pageable);

    /**
     * 通过 task 查询所有结果
     *
     * @param task task
     * @return 结果
     */
    List<File> findAllByTask(Task task);

    /**
     * 通过 name 查询所有结果
     *
     * @param name name
     * @return 结果
     */
    Optional<File> findFirstByName(String name);
}
