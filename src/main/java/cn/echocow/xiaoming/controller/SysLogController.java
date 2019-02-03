package cn.echocow.xiaoming.controller;

import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping("/sysLogs")
public class SysLogController {

    private final SysLogService sysLogService;

    @Autowired
    public SysLogController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 获取所有的日志
     * Get    /sysLogs
     *
     * @return http 响应
     */
    @GetMapping
    @PageResult
    public HttpEntity<?> sysLogs(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return (page == null || size == null || page <= 0 || size <= 0) ?
                ResponseEntity.ok(sysLogService.findAllResources(this.getClass())) :
                ResponseEntity.ok(sysLogService.findAll(PageRequest.of(--page, size), this.getClass()));
    }


    /**
     * 获取指定 id 的日志
     * Get    /sysLogs/{id}
     *
     * @param id 日志id
     * @return http 响应
     */
    @GetMapping("/{id}")
    public HttpEntity<?> sysLog(@PathVariable Long id) {
        return ResponseEntity.ok(new RestResource<>(sysLogService.findById(id), this.getClass()));
    }
}
