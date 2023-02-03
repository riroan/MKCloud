package com.openbox.backend.service;

import com.openbox.backend.domain.FileEntity;
import com.openbox.backend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public void save(MultipartFile multipartFile, String owner) {
        if (multipartFile.isEmpty()) {
            return;
        }
        FileEntity savedFile = new FileEntity();
        String originalFileName = multipartFile.getOriginalFilename();
        Long fileSize = multipartFile.getSize();

        String storeFileName = createStoreFileName(originalFileName);
        String fullPath = getFullPath(storeFileName, owner);
        String folderPath = getFullPath("", owner);
        File folder = new File(folderPath);
        if (folder.mkdirs()) {
            log.info("new folder created, path = {}", folderPath);
        }

        try {
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        savedFile.setFileName(originalFileName);
        savedFile.setFileSize(fileSize);
        savedFile.setOwner(owner);
        savedFile.setUploadTime(LocalDateTime.now());
        savedFile.setStoreFileName(storeFileName);

        fileRepository.save(savedFile);
    }

    public void saveAll(List<MultipartFile> multipartFiles, String owner) {
        for (MultipartFile multipartFile : multipartFiles) {
            save(multipartFile, owner);
        }
    }

    public void deleteOne(Long id, String owner) {
        FileEntity file = fileRepository.findById(id);
        if (file == null) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }
        if (!file.getOwner().equals(owner)) {
            throw new RuntimeException("사용자의 파일이 아닙니다.");
        }
        fileRepository.deleteById(id);
    }

    public List<FileEntity> findByOwner(String owner) {
        return fileRepository.findByOwner(owner);
    }

    public FileEntity findOne(Long id) {
        return fileRepository.findById(id);
    }

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    public String getFullPath(String filename, String owner) {
        return fileDir + owner + "/" + filename;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
