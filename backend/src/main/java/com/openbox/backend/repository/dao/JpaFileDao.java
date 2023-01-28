package com.openbox.backend.repository.dao;

import com.openbox.backend.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaFileDao extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(final String owner);
}
