package com.openbox.backend.repository;

import com.openbox.backend.domain.FileEntity;

import java.util.List;

public interface FileRepository {
    FileEntity save(FileEntity file);

    FileEntity findById(Long id);

    List<FileEntity> findByOwner(String owner);

    List<FileEntity> findAll();


}
