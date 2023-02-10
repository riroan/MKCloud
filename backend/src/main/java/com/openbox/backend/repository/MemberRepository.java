package com.openbox.backend.repository;

import com.openbox.backend.domain.MemberEntity;

public interface MemberRepository {
    MemberEntity register(String id, String password);

    MemberEntity findById(String id);

    Boolean existedById(String id);

    Boolean checkLoginInfo(String id, String password);

    Boolean isPending(String id);

    void changePassword(String id, String password);
}
