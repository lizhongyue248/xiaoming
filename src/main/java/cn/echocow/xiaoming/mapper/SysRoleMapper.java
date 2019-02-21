package cn.echocow.xiaoming.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.echocow.xiaoming.model.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过权限 id 查询所有角色
     *
     * @param id 权限 id
     * @return 角色
     */
    List<SysRole> findByPermissionId(@Param("id")Long id);


}