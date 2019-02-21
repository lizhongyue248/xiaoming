package cn.echocow.xiaoming.mapper;
import org.apache.ibatis.annotations.Param;

import cn.echocow.xiaoming.model.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 通过 id 查找完整的 task 信息
     *
     * @param id id
     * @return 结果
     */
    Task findFirstCompleteById(@Param("id")Long id);



}