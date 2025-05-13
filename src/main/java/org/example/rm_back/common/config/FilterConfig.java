package org.example.rm_back.common.config;

import org.example.rm_back.global.filter.JwtAuthenticationFilter;
import org.example.rm_back.global.jwt.JwtProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(JwtProvider jwtProvider){
        var bean = new FilterRegistrationBean<>(new JwtAuthenticationFilter(jwtProvider));
        bean.addUrlPatterns("/api/**");
        return bean;
    }
}
