package cn.echocow.xiaoming.repository.app;

import cn.echocow.xiaoming.entity.app.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 作业
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
