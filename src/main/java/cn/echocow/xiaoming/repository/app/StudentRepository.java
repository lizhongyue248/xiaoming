package cn.echocow.xiaoming.repository.app;

import cn.echocow.xiaoming.entity.app.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 学生
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
