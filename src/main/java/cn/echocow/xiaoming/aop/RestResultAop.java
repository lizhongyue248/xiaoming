package cn.echocow.xiaoming.aop;

import cn.echocow.xiaoming.resource.ApplicationResources;
import cn.echocow.xiaoming.resource.annotation.PageResult;
import cn.echocow.xiaoming.resource.helper.PageInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
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
        Method method = signature.getMethod();
        PageResult annotation = method.getAnnotation(PageResult.class);
        if (annotation == null) {
            return;
        }
        try {
            ResponseEntity res = (ResponseEntity) result;
            if (!res.hasBody()) {
                return;
            }
            if (!(res.getBody() instanceof ApplicationResources)) {
                return;
            }
            ApplicationResources resources = (ApplicationResources) res.getBody();
            if (resources == null) {
                return;
            }
            PageInfo pageInfo = resources.getPage();
            if (pageInfo == null) {
                return;
            }
            Integer size = pageInfo.getSize();
            Integer page = pageInfo.getNumber();
            String uri = ControllerLinkBuilder.linkTo(method, page + 1, size).toString();
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
