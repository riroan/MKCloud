package com.openbox.backend.repository;

import com.openbox.backend.domain.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        if (memberRepository instanceof MemoryMemberRepository) {
            ((MemoryMemberRepository) memberRepository).clearStore();
        }
    }

    @Test
    @DisplayName(value = "회원가입")
    void createUser() {
        String id = "test1";
        String password = "fakePassword";

        MemberEntity member = memberRepository.register(id, password);

        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName(value = "이미 존재하는 아이디 가입")
    void duplicatedRegister() {
        String id = "test1";
        String password = "fakePassword";

        memberRepository.register(id, password);
        assertThatThrownBy(() -> memberRepository.register(id, password)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName(value = "해당 아이디가 존재하는지 확인")
    void existedById() {
        String id = "test1";
        String password = "fakePassword";

        memberRepository.register(id, password);

        assertThat(memberRepository.existedById("test1")).isEqualTo(true);
        assertThat(memberRepository.existedById("test2")).isEqualTo(false);
    }

    @Test
    @DisplayName(value = "로그인 정보 확인")
    void checkLoginInfo() {
        String id = "test1";
        String password = "fakePassword";

        MemberEntity member = memberRepository.register(id, password);

        assertThat(memberRepository.checkLoginInfo("test1", "wrongPassword")).isEqualTo(false);
        assertThat(memberRepository.checkLoginInfo("wrongId", "fakePassword")).isEqualTo(false);
        assertThat(memberRepository.checkLoginInfo("test1", "fakePassword")).isEqualTo(true);
    }

}