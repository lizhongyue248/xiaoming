package cn.echocow.xiaoming.service;

import cn.echocow.xiaoming.entity.sys.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-31 13:55
 */
public interface SysLogService {

    /**
     * 日志保存操作
     *
     * @param sysLog 保存的日志
     * @return 保存后的只
     */
    SysLog save(SysLog sysLog);


    /**
     * 查找所有日志
     *
     * @return 所有日志
     */
    List<SysLog> findAll();

    /**
     * 分页查找日志
     *
     * @param pageable 分页参数
     * @return 日志
     */
    Page<SysLog> findAll(Pageable pageable);

    /**
     * 通过用户 id 查询日志
     *
     * @param id 日志 id
     * @return 结果
     */
    SysLog findById(long id);
}
