package com.interest.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 类描述：设置CORS过滤器
 *
 * @author
 */
@Configuration
public class MyConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*")
                        .allowedOrigins("*").exposedHeaders("x-total-count","x-auth-token")
                        .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");
            }
        };
    }
}
