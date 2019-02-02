package cn.echocow.xiaoming.resource.annotation;

import java.lang.annotation.*;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-02 15:57
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageResult {
}
