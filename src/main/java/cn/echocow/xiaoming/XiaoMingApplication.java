package cn.echocow.xiaoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-01-23 00:02
 */
@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class XiaoMingApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoMingApplication.class, args);
    }

}
