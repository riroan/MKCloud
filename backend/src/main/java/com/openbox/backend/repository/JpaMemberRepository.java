package com.openbox.backend.repository;

import com.openbox.backend.domain.MemberEntity;
import com.openbox.backend.repository.dao.JpaMemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository{
    private final JpaMemberDao jpaMemberDao;

    @Override
    public MemberEntity register(String id, String password) {
        if (existedById(id)){
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        MemberEntity member = new MemberEntity(id, password);
        jpaMemberDao.save(member);
        return member;
    }

    @Override
    public MemberEntity findById(String id) {
        MemberEntity member = jpaMemberDao.findById(id).orElseThrow();
        return member;
    }

    @Override
    public Boolean existedById(String id) {
        return jpaMemberDao.existsById(id);
    }

    @Override
    public Boolean checkLoginInfo(String id, String password) {
        return jpaMemberDao.existsByIdAndPassword(id, password);
    }

    @Override
    public void changePassword(String id, String password) {
        MemberEntity findMember = jpaMemberDao.findById(id).orElseThrow();
        findMember.setPassword(password);
        jpaMemberDao.save(findMember);
    }
}
