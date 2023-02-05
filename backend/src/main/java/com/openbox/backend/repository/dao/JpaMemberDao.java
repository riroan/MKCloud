package com.openbox.backend.repository.dao;


import com.openbox.backend.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberDao extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(String id);

    Boolean existsById(String id);

    Boolean existsByIdAndPassword(String id, String password);
}
