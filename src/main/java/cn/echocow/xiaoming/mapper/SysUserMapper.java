package cn.echocow.xiaoming.mapper;

import org.apache.ibatis.annotations.Param;

import cn.echocow.xiaoming.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-20 19:48
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询,查询角色等信息
     *
     * @param user 用户名
     * @return 结果
     */
    SysUser findByUser(@Param("user") SysUser user);

    /**
     * 通过身份查找用户，查询顺序如下
     * 1. 用户名
     * 2. 邮箱
     * 3. 手机号
     *
     * @param identity 身份
     * @return 结果
     */
    SysUser findByIdentity(@Param("identity")String identity);

    /**
     * 通过用户名简单查询
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser findSimpleByUsernameAndEnabledTrue(@Param("username") String username);

    /**
     * 通过身份信息简单查询
     *
     * @param identity 身份信息
     * @return 结果
     */
    SysUser findSimpleByIdentity(@Param("identity") String identity);
}