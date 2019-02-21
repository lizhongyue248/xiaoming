package cn.echocow.xiaoming.mapper;

import cn.echocow.xiaoming.model.entity.Homework;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作业
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface HomeworkMapper extends BaseMapper<Homework> {
}