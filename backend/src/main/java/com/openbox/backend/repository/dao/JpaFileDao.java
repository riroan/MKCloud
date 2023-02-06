package com.openbox.backend.repository.dao;

import com.openbox.backend.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaFileDao extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(final String owner);

    @Query(value = "select sum(file.fileSize) from FileEntity file where file.owner=:owner")
    Long getFileSizeSum(@Param(value = "owner") final String owner);
}
