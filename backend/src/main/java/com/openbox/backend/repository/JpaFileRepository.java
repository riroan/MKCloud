package com.openbox.backend.repository;

import com.openbox.backend.domain.FileEntity;
import com.openbox.backend.repository.dao.JpaFileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaFileRepository implements FileRepository {
    private final JpaFileDao jpaFileDao;

    @Override
    public FileEntity save(FileEntity file) {
        FileEntity savedFile = jpaFileDao.save(file);
        return savedFile;
    }

    @Override
    public FileEntity findById(Long id) {
        FileEntity findFile = jpaFileDao.findById(id).orElseThrow();
        return findFile;
    }

    @Override
    public List<FileEntity> findByOwner(String owner) {
        List<FileEntity> result = jpaFileDao.findByOwner(owner);
        return result;
    }

    @Override
    public List<FileEntity> findAll() {
        List<FileEntity> result = jpaFileDao.findAll();
        return result;
    }

    @Override
    public void deleteById(Long id) {
        jpaFileDao.deleteById(id);
    }
}
