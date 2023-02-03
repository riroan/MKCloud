package com.openbox.backend.repository;


import com.openbox.backend.domain.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Repository
public class MemoryMemberRepository implements MemberRepository {
    Map<String, MemberEntity> store = new HashMap<>();
    Long sequence = 0L;

    @Override
    public MemberEntity register(String id, String password) {
        if (existedById(id)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        MemberEntity member = new MemberEntity(id, password);
        member.setIdx(++sequence);
        store.put(id, member);
        return member;
    }

    @Override
    public MemberEntity findById(String id) {
        MemberEntity member = store.get(id);
        return member;
    }

    @Override
    public Boolean existedById(String id) {
        MemberEntity member = store.get(id);
        return member != null;
    }

    @Override
    public Boolean checkLoginInfo(String id, String password) {
        MemberEntity member = store.get(id);
        if (member == null) {
            return false;
        }
        return member.getPassword().equals(password);
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}
