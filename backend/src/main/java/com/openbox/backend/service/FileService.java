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

    public void save(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return;
        }
        FileEntity savedFile = new FileEntity();
        String originalFileName = multipartFile.getOriginalFilename();
        Long fileSize = multipartFile.getSize();

        String storeFileName = createStoreFileName(originalFileName);

        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        savedFile.setFileName(originalFileName);
        savedFile.setFileSize(fileSize);
        savedFile.setOwner("test");
        savedFile.setUploadTime(LocalDateTime.now());

        fileRepository.save(savedFile);
    }

    public void saveAll(List<MultipartFile> multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            save(multipartFile);
        }
    }

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    private String getFullPath(String filename) {
        return fileDir + filename;
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
