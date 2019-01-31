package cn.echocow.xiaoming.resource.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

/**
 * 错误资源
 *
 * @author Echo
 * @version 1.0
 * @date 2019-01-05 22:51
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ErrorResource {
    private String message;

}
