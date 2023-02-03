package com.openbox.backend.config;

import com.openbox.backend.controller.interceptor.LoginInterceptor;
import com.openbox.backend.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/login")
                .excludePathPatterns("/api/v1/register")
                .excludePathPatterns("/api/v1/logout");
    }
}
