package cn.echocow.xiaoming.mapper;

import org.apache.ibatis.annotations.Param;

import cn.echocow.xiaoming.model.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

    /**
     * 通过名称和 task id 查询
     *
     * @param name   名称
     * @param taskId taskId
     * @return 结果
     */
    File findFirstByNameAndTaskId(@Param("name") String name, @Param("taskId") Long taskId);

}