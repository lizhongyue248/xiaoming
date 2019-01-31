package cn.echocow.xiaoming;

import org.apache.commons.lang3.StringUtils;
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
    @Test
    public void enumsTest() {
        String s = "123456789";
        String left = StringUtils.left(s, 5);
        System.out.println(left);
    }
}
