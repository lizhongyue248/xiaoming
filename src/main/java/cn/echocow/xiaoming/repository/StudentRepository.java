package cn.echocow.xiaoming.repository;

import cn.echocow.xiaoming.base.BaseRepository;
import cn.echocow.xiaoming.model.entity.Student;
import cn.echocow.xiaoming.model.entity.SysUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 学生
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 18:35
 */
@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {
    /**
     * 通过用户查询学生
     *
     * @param user 用户
     * @return 学生
     */
    Optional<Student> findFirstByUser(SysUser user);
}
