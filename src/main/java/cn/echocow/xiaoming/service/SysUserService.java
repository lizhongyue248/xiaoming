package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.entity.sys.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:39
 */
public interface SysUserService {
    /**
     * 通过用户名加载用户
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser loadUserByUsername(String username);

    /**
     * 通过用户名查找启用的用户的 ID
     *
     * @param username 用户名
     * @return 结果
     */
    Optional<Long> findFirstIdByUsernameAndEnabledTrue(String username);

    /**
     * 查找所有用户
     *
     * @return 所有用户
     */
    List<SysUser> findAll();

    /**
     * 分页查找用户
     *
     * @param pageable 分页参数
     * @return 用户
     */
    Page<SysUser> findAll(Pageable pageable);

    /**
     * 通过用户 id 查询用户
     *
     * @param id 用户 id
     * @return 结果
     */
    SysUser findById(Long id);

    /**
     * 删除指定 id 的用户
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 保存用户
     *
     * @param sysUser 用户
     * @return 保存的用户
     */
    SysUser save(SysUser sysUser);


    /**
     * 更新用户
     *
     * @param id 被更新的id
     * @param sysUser 更新的用户
     * @return 更新后的用户
     */
    SysUser update(Long id, SysUser sysUser);


}
