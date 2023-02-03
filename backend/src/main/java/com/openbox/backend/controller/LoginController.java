package com.openbox.backend.controller;

import com.openbox.backend.controller.dto.LoginRequest;
import com.openbox.backend.domain.MemberEntity;
import com.openbox.backend.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();
        if (!memberService.checkLoginInfo(id, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = memberService.createToken(id);
        Cookie cookie = new Cookie("mkcloud_authentication", token);
        log.info("token={}", token);
        cookie.setMaxAge(15 * 24 * 60 * 60 * 1000);
        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody LoginRequest loginRequest) {
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();
        log.info(id);
        log.info(password);
        try {
            memberService.register(id, password);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        log.info("logout");
        expireCookie(response, "mkcloud_authentication");
        expireCookie(response, "authentication");
        expireCookie(response, "key");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
