package cn.echocow.xiaoming.controller.sys;

import cn.echocow.xiaoming.entity.sys.SysLog;
import cn.echocow.xiaoming.resource.ApplicationResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.resource.helper.PageInfo;
import cn.echocow.xiaoming.resource.sys.SysLogResource;
import cn.echocow.xiaoming.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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
        if (page == null || size == null || page <= 0 || size <= 0) {
            return ResponseEntity.ok(new Resources<>(sysLogService.findAll()
                    .stream().map(SysLogResource::new)
                    .collect(Collectors.toList())));
        }
        page--;
        Page<SysLog> sysLogs = sysLogService.findAll(PageRequest.of(page, size));
        ApplicationResources<SysLogResource> resources = new ApplicationResources<>(sysLogs.stream()
                .map(SysLogResource::new).collect(Collectors.toList()));
        resources.setPage(new PageInfo(size, sysLogs.getNumber() + 1, sysLogs.getTotalElements(),
                sysLogs.getTotalPages(), sysLogs.hasPrevious(), sysLogs.hasNext()));
        return ResponseEntity.ok(resources);
    }


    /**
     * 获取指定 id 的日志
     * Get    /sysLogs/{id}
     *
     * @param id 日志id
     * @return http 响应
     */
    @GetMapping("/{id}")
    public HttpEntity<?> sysLog(@PathVariable long id) {
        return ResponseEntity.ok(new SysLogResource(sysLogService.findById(id)));
    }
}
