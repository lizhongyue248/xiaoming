package cn.echocow.xiaoming.aop;

import cn.echocow.xiaoming.resource.RestResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.resource.PageInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 对于分页的集合，进行添加分页信息
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 15:55
 */
@Component
@Aspect
public class RestResultAop {

    @Pointcut("@annotation(cn.echocow.xiaoming.resource.annotation.PageResult)")
    public void pageResult() { }

    @AfterReturning(value = "pageResult()", returning = "result")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PageResult annotation = signature.getMethod().getAnnotation(PageResult.class);
        if (annotation == null) {
            return;
        }
        try {
            ResponseEntity res = (ResponseEntity) result;
            if (!res.hasBody()) {
                return;
            }
            if (!(res.getBody() instanceof RestResources)) {
                return;
            }
            RestResources resources = (RestResources) res.getBody();
            if (resources == null) {
                return;
            }
            PageInfo pageInfo = resources.getPage();
            if (pageInfo == null) {
                return;
            }
            Integer size = pageInfo.getSize();
            Integer page = pageInfo.getNumber();
            // 尝试多次，只能手动封装
            String uri = ControllerLinkBuilder.linkTo(joinPoint.getTarget().getClass()).toString();
            resources.add(new Link(uri + "?page=" + page + "&size=" + size).withSelfRel());
            if (pageInfo.hasPrevioud()) {
                resources.add(new Link(uri + "?page=" + (page - 1) + "&size=" + size).withRel(Link.REL_PREVIOUS));
            }
            if (pageInfo.hasNext()) {
                resources.add(new Link(uri + "?page=" + (page + 1) + "&size=" + size).withRel(Link.REL_NEXT));
            }
            resources.add(new Link(uri + "?page=" + 1 + "&size=" + size).withRel(Link.REL_FIRST));
            resources.add(new Link(uri + "?page=" + pageInfo.getTotalPages() + "&size=" + size).withRel(Link.REL_LAST));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
