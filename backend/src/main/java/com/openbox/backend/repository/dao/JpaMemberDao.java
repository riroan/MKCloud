package com.openbox.backend.repository.dao;


import com.openbox.backend.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberDao extends JpaRepository<MemberEntity, Long> {
    MemberEntity findById(String id);

    Boolean existsById(String id);

    Boolean existsByIdAndPassword(String id, String password);
}
