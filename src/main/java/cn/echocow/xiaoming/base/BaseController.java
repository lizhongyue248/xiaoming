package cn.echocow.xiaoming.base;

import cn.echocow.xiaoming.exception.InvalidRequestException;
import cn.echocow.xiaoming.exception.ResourceNoFoundException;
import cn.echocow.xiaoming.exception.ServiceException;
import cn.echocow.xiaoming.resource.ApplicationResource;
import cn.echocow.xiaoming.resource.PageSimple;
import cn.echocow.xiaoming.resource.RestResource;
import cn.echocow.xiaoming.resource.RestResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.utils.CustomBeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-03 21:43
 */
@SuppressWarnings("all")
public abstract class BaseController<T extends BaseEntity, S extends BaseService<T>> {

    @Autowired
    private S baseService;

    /**
     * 获取控制器，通过反射添加 rest hateoas
     *
     * @return 控制器
     */
    public abstract Class getControllerClass();

    /**
     * 保存一个资源
     * POST    /{entity}
     *
     * @param entity        实体
     * @param bindingResult 检验结果
     * @return http 响应
     */
    @PostMapping
    public HttpEntity<?> saveResource(@Valid @RequestBody T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        entity.setId(null);
        if (!baseService.save(entity)){
            throw new ServiceException("the resource save failed!");
        }
        return new ResponseEntity<>(new RestResource<>(entity, getControllerClass()), HttpStatus.CREATED);
    }

    /**
     * 删除指定 id 的资源
     * DELETE   /sysUsers/{id}
     *
     * @param id 资源 id
     * @return http 响应
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteResource(@PathVariable Long id) {
        baseService.removeById(id);
        return new ResponseEntity<>(new ApplicationResource(), HttpStatus.NO_CONTENT);
    }

    /**
     * 更新一个资源，提供当前资源的所有信息
     * PUT    /{entity}/{id}
     *
     * @param id            资源 id
     * @param entity        更新后的资源
     * @param bindingResult 参数校验
     * @return http 响应
     */
    @PutMapping("/{id}")
    public HttpEntity<?> putResource(@PathVariable Long id, @Valid @RequestBody T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        return patchResource(id, entity);
    }

    /**
     * 更新一个资源，提供当前资源的部分信息
     * PATCH    /{entity}/{id}
     *
     * @param id     资源 id
     * @param entity 更新后的资源
     * @return http 响应
     */
    @PatchMapping("/{id}")
    public HttpEntity<?> patchResource(@PathVariable Long id, @RequestBody T entity) {
        entity.setId(id);
        T exist = Optional.ofNullable(baseService.getById(id)).orElseThrow(
                () -> new ResourceNoFoundException(id));
        BeanUtils.copyProperties(entity, exist, CustomBeanUtils.getNullPropertyNames(entity));
        if (!baseService.updateById(exist)){
            throw new ServiceException("the resource update failed!");
        }
        return ResponseEntity.ok(new RestResource<>(entity, getControllerClass()));
    }

    /**
     * 获取指定 id 的资源
     * Get    /{entity}/{id}
     *
     * @param id 资源 id
     * @return http 响应
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getResource(@PathVariable Long id) {
        return ResponseEntity.ok(new RestResource<>(baseService.getById(id), getControllerClass()));
    }

    /**
     * 获取所有资源/分页
     *
     * @param page 页码
     * @param size 大小
     * @return http 响应
     */
    @GetMapping
    @PageResult
    public HttpEntity<?> getAllOrPagesResources(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null || page <= 0 || size <= 0) {
            List<T> all = baseService.list();
            return ResponseEntity.ok(new Resources<>(all.stream()
                    .map(entity -> new RestResource<>(entity, getControllerClass()))
                    .collect(Collectors.toList())));
        }
        IPage<T> result = baseService.page(new Page<>(page, size));
        RestResources<RestResource> resources = new RestResources<>(result.getRecords().stream()
                .map(entity -> new RestResource<>(entity, getControllerClass()))
                .collect(Collectors.toList()));
        resources.setPage(new PageSimple(result.getSize(), result.getCurrent(), result.getTotal(), result.getPages()));
        return ResponseEntity.ok(resources);
    }

}
