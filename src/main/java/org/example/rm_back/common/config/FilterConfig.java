package org.example.rm_back.common.config;

import lombok.RequiredArgsConstructor;
import org.example.rm_back.common.config.redis.TokenListStrategyFactory;
import org.example.rm_back.global.filter.JwtAuthenticationFilter;
import org.example.rm_back.global.jwt.JwtProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtProvider jwtProvider;
    private final TokenListStrategyFactory strategyFactory;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtProvider, strategyFactory));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
