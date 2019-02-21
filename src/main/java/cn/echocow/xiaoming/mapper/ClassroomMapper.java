package cn.echocow.xiaoming.mapper;

import cn.echocow.xiaoming.model.entity.Classroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 班级
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {
}