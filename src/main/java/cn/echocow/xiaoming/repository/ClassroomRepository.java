package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.model.entity.Classroom;
import cn.echocow.xiaoming.base.BaseRepository;
import org.springframework.stereotype.Repository;


/**
 * 班级
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface ClassroomRepository extends BaseRepository<Classroom, Long> {

}
