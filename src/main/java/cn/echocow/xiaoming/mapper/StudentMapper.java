package cn.echocow.xiaoming.mapper;
import org.apache.ibatis.annotations.Param;

import cn.echocow.xiaoming.model.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 通过用户 id 查询
     *
     * @param userId 用户 id
     * @return 结果
     */
    Student findFirstByUserId(@Param("userId")Long userId);

}