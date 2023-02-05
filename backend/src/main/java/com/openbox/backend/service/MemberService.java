package com.openbox.backend.service;

import com.openbox.backend.repository.MemberRepository;
import com.openbox.backend.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void register(String id, String password) {
        if (memberRepository.existedById(id)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        memberRepository.register(id, password);
    }

    public Boolean checkLoginInfo(String id, String password) {
        return memberRepository.checkLoginInfo(id, password);
    }

    public String createToken(String id) {
        return jwtTokenProvider.createToken(id);
    }

    public void changePassword(String id, String newPassword) {
        memberRepository.changePassword(id, newPassword);
    }
}
