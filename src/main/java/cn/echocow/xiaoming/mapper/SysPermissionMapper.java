package cn.echocow.xiaoming.mapper;
import org.apache.ibatis.annotations.Param;

import cn.echocow.xiaoming.model.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 通过角色id查询所有的权限
     *
     * @param roleIds 角色 id
     * @return 结果
     */
    List<SysPermission> findAllByRoleIds(@Param("idList")List<Long> roleIds);

}