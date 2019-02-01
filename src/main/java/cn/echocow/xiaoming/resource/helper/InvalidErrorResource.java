package cn.echocow.xiaoming.resource.helper;

import cn.echocow.xiaoming.resource.ApplicationResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 信息封装
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:52
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class InvalidErrorResource extends ApplicationResource {
    /**
     * 信息
     */
    private String message;
    /**
     * 具体错误
     */
    private Object errors;
}
