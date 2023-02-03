package com.openbox.backend.controller.interceptor;

import com.openbox.backend.support.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor{
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("method : {}", request.getMethod());
        log.info("path : {}", request.getContextPath());
        log.info("url : {}", request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            log.info("name={}", name);
            if(name.equals("mkcloud_authentication")){
                token = cookie.getValue();
                log.info("token = {}", token);
                break;
            }
        }
        if(token == null) {
            // login 필요
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            String subject = jwtTokenProvider.getSubject(token);
            return true;
        }catch(RuntimeException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
