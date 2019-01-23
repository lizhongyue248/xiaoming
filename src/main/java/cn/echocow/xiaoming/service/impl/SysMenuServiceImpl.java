package cn.echocow.xiaoming.service.impl;

import cn.echocow.xiaoming.entity.SysMenu;
import cn.echocow.xiaoming.repository.SysMenuRepository;
import cn.echocow.xiaoming.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 22:01
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuRepository sysMenuRepository;

    @Override
    public List<SysMenu> findAll() {
        return sysMenuRepository.findAll();
    }
}
