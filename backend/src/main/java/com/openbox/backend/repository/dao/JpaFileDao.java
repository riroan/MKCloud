package com.openbox.backend.repository.dao;

import com.openbox.backend.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaFileDao extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(final String owner);

    @Query(value = "select file from FileEntity file where file.owner = :owner and file.isDeleted = :isDeleted")
    List<FileEntity> findByOwner(final String owner, final Boolean isDeleted);

    @Query(value = "select sum(file.fileSize) from FileEntity file where file.owner=:owner and file.isDeleted=:isDeleted")
    Long getFileSizeSum(@Param(value = "owner") final String owner, @Param(value="isDeleted") final Boolean isDeleted);
}
