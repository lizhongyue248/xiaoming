package cn.echocow.xiaoming.controller.sys;

import cn.echocow.xiaoming.exception.InvalidRequestException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.resource.ApplicationResource;
import cn.echocow.xiaoming.resource.ApplicationResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.resource.helper.PageInfo;
import cn.echocow.xiaoming.resource.sys.SysUserResource;
import cn.echocow.xiaoming.entity.sys.SysUser;
import cn.echocow.xiaoming.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-30 22:37
 */
@RestController
@RequestMapping("/sysUsers")
public class SysUserController {
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 获取所有的用户
     * Get    /sysUsers
     *
     * @return http 响应
     */
    @GetMapping
    @PageResult
    public HttpEntity<?> sysUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null || page <= 0 || size <= 0) {
            return ResponseEntity.ok(new Resources<>(sysUserService.findAll()
                    .stream().map(SysUserResource::new)
                    .collect(Collectors.toList())));
        }
        page--;
        Page<SysUser> sysUsers = sysUserService.findAll(PageRequest.of(page, size));
        ApplicationResources<SysUserResource> resources = new ApplicationResources<>(sysUsers.stream()
                .map(SysUserResource::new).collect(Collectors.toList()));
        resources.setPage(new PageInfo(size, sysUsers.getNumber() + 1, sysUsers.getTotalElements(),
                sysUsers.getTotalPages(), sysUsers.hasPrevious(), sysUsers.hasNext()));
        return ResponseEntity.ok(resources);
    }

    /**
     * 获取指定 id 的用户
     * Get    /sysUsers/{id}
     *
     * @param id 用户id
     * @return http 响应
     */
    @GetMapping("/{id}")
    public HttpEntity<?> sysUser(@PathVariable long id) {
        return ResponseEntity.ok(new SysUserResource(sysUserService.findById(id)));
    }

    /**
     * 添加一个用户
     * POST    /sysUsers
     *
     * @param sysUser       用户
     * @param bindingResult 参数校验
     * @return http 响应
     */
    @PostMapping
    public HttpEntity<?> saveSysUser(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        sysUser.setId(null);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        return new ResponseEntity<>(new SysUserResource(sysUserService.save(sysUser)), HttpStatus.CREATED);
    }

    /**
     * 更新一个用户，提供当前用户的所有信息
     * PUT    /sysUsers/{id}
     *
     * @param id            更新的id
     * @param sysUser       更新后的书单
     * @param bindingResult 参数校验
     * @return http 响应
     */
    @PutMapping("/{id}")
    public HttpEntity<?> putSysUser(@PathVariable Long id, @Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        return patchSysUser(id, sysUser);
    }

    /**
     * 更新一个用户，提供当前用户的部分信息
     * PATCH    /sysUsers/{id}
     *
     * @param id      更新的id
     * @param sysUser 更新后的书单
     * @return http 响应
     */
    @PatchMapping("/{id}")
    public HttpEntity<?> patchSysUser(@PathVariable Long id, @RequestBody SysUser sysUser) {
        if (StringUtils.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        }
        return ResponseEntity.ok(new SysUserResource(sysUserService.update(id, sysUser)));
    }

    /**
     * 删除指定 id 的用户
     * DELETE   /sysUsers/{id}
     *
     * @param id 用户id
     * @return http 响应
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSysUser(@PathVariable long id) {
        sysUserService.deleteById(id);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }
}
