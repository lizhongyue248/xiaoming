package cn.echocow.xiaoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.config.EnableEntityLinks;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 00:02
 */
@SpringBootApplication
@EnableJpaAuditing
public class XiaoMingApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoMingApplication.class, args);
    }

}
