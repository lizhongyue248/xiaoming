package cn.echocow.xiaoming;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 20:24
 */
public class PasswordTest {
    @Test
    public void bcrypt() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
