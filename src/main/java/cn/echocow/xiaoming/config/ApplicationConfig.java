package cn.echocow.xiaoming.config;

import cn.echocow.xiaoming.intercepter.AuthInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-11 11:54
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Bean
    public HandlerInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/auth/**/**");
    }

    @Bean
    public HttpClient httpClient(){
        return HttpClients.createDefault();
    }

}
