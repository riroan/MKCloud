package com.openbox.backend.service;

import com.openbox.backend.domain.MemberEntity;
import com.openbox.backend.repository.MemberRepository;
import com.openbox.backend.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Value(value = "${encrypt.algorithm}")
    private String algorithm;

    public void register(String id, String password) {
        if (memberRepository.existedById(id)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        String encryptedPassword = encrypt(password);
        memberRepository.register(id, encryptedPassword);
    }

    public MemberEntity findById(String id) {
        return memberRepository.findById(id);
    }

    public Boolean checkLoginInfo(String id, String password) {
        String encryptedPassword = encrypt(password);
        return memberRepository.checkLoginInfo(id, encryptedPassword);
    }

    public String createToken(String id) {
        return jwtTokenProvider.createToken(id);
    }

    public void changePassword(String id, String newPassword) {
        String encryptedPassword = encrypt(newPassword);
        memberRepository.changePassword(id, encryptedPassword);
    }

    private String encrypt(String message) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(message.getBytes());
            byte[] data = md.digest();
            for (byte b : data) {
                sb.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            log.info("그런 알고리즘은 없습니다.");
            log.error("error", e);
        }
        log.info("hashed : {}", sb.toString());
        return sb.toString();
    }
}
