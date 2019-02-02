package cn.echocow.xiaoming;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;

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
        String left = StringUtils.left(s, 111);
        System.out.println(left);
    }

    @Test
    public void stringUtilsTest(){
        String s1 = "123456";
        String s2 = "123426";
        String s4 = "1";
        String s5 = "123";

        System.out.println(StringUtils.compare(s1, s2));
        System.out.println(StringUtils.compare(s4, s5));
    }

    @Test
    public void timeTest() {
        System.out.println(Duration.ofDays(1).getSeconds());
    }
}
