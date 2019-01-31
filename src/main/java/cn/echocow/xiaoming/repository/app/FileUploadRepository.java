package cn.echocow.xiaoming.repository.app;

import cn.echocow.xiaoming.entity.app.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件上传
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface FileUploadRepository extends JpaRepository<File, Long> {
}
