package org.example.rm_back.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.rm_back.auth.AuthUserArgumentResolver;
import org.example.rm_back.global.PermissionService;
import org.example.rm_back.global.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final PermissionService permissionService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(permissionService))
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/v1/auth/signin");
    }
}
