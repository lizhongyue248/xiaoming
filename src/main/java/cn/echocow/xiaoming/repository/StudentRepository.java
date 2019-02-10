package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.model.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * 学生
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {
}
