package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.entity.File;
import org.springframework.stereotype.Repository;

/**
 * 文件上传
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface FileRepository extends BaseRepository<File, Long> {
}
