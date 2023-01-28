package com.openbox.backend.repository;

import com.openbox.backend.domain.FileEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
public class MemoryFileRepository implements FileRepository {
    Map<Long, FileEntity> store = new HashMap<>();
    Long sequence = 0L;

    @Override
    public FileEntity save(FileEntity file) {
        file.setId(++sequence);
        store.put(sequence, file);
        return file;
    }

    @Override
    public FileEntity findById(Long id) {
        FileEntity file = store.get(id);
        return file;
    }

    @Override
    public List<FileEntity> findByOwner(String owner) {
        List<FileEntity> result = new ArrayList<>();
        for (FileEntity file : store.values()) {
            if (file.getOwner().equals(owner)) {
                result.add(file);
            }
        }
        return result;
    }

    @Override
    public List<FileEntity> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    public void clearStore(){
        store.clear();
    }
}
