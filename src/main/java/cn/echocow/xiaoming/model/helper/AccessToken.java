package cn.echocow.xiaoming.model.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 令牌信息
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-14 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

    /**
     * 令牌
     */
    private String accessToken;
    /**
     * 类型
     */
    private String tokenType;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 有效期
     */
    private int expiresIn;
    /**
     * 范围
     */
    private Set<String> scope;
    /**
     * jti
     */
    private String jti;

}
