package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.entity.SysMenu;

import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 21:13
 */
public interface SysMenuService {
    /**
     * 查找所有
     *
     * @return 结果
     */
    List<SysMenu> findAll();
}
