package cn.echocow.xiaoming.repository.sys;

import cn.echocow.xiaoming.entity.sys.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 16:25
 */
@Repository
public interface SysLogRepository extends JpaRepository<SysLog, Long> {
}
