package cn.echocow.xiaoming.repository.app;

import cn.echocow.xiaoming.entity.app.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 班级
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface ClassesRepository extends JpaRepository<Classroom, Long> {

}
