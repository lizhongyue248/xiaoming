package cn.echocow.xiaoming.model.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-13 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode implements Serializable {
    /**
     * 验证码
     */
    private String code;

    /**
     * 到期时间
     */
    private LocalDateTime expireTime;

    /**
     * 构造
     *
     * @param code 验证码
     * @param expireIn 有效时间/秒
     */
    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 是否超时
     *
     * @return 结果
     */
    public boolean isAfter() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
