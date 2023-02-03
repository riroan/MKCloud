package com.openbox.backend.service;

import com.openbox.backend.support.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class JwtTokenProviderTest {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName(value = "토큰 생성")
    void createToken() {
        String id = "user1";

        String token = jwtTokenProvider.createToken(id);

        String subject = jwtTokenProvider.getSubject(token);
        assertThat(subject).isEqualTo(id);
    }

    @Test
    @DisplayName(value = "다른 사람의 토큰")
    void otherToken() {
        String id1 = "user1";
        String id2 = "user2";
        String token1 = jwtTokenProvider.createToken(id1);

        String subject = jwtTokenProvider.getSubject(token1);

        assertThat(id2).isNotEqualTo(subject);
    }

    @Test
    @DisplayName(value = "잘못된 토큰")
    void invalidToken() {
        String fakeToken = "aaa.bbb.ccc";
        assertThatThrownBy(() -> jwtTokenProvider.getSubject(fakeToken))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName(value = "만료된 토큰")
    void expiredToken() throws InterruptedException {
        String id = "user";
        String token = jwtTokenProvider.createToken(id);

        Thread.sleep(1100L);

        assertThatThrownBy(() -> jwtTokenProvider.getSubject(token)).isInstanceOf(RuntimeException.class);
    }
}