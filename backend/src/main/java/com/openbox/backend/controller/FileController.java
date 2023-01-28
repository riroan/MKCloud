package com.openbox.backend.controller;

import com.openbox.backend.controller.dto.SaveFileRequest;
import com.openbox.backend.domain.FileEntity;
import com.openbox.backend.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;

    @PostMapping("/save")
    public String save(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            log.info("no files");
            return "REJECT";
        }
        for (MultipartFile multipartFile : multipartFiles) {
            log.info("file = {}", multipartFile);
        }
        fileService.saveAll(multipartFiles);
        return "123aa";
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileEntity>> getAll() {
        List<FileEntity> result = fileService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadOne(@PathVariable Long fileId) throws MalformedURLException {
        FileEntity file = fileService.findOne(fileId); // ! TODO :  NoFileException
        String storeFileName = file.getStoreFileName();
        String fileName = file.getFileName();

        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(storeFileName));
        log.info("uploadFileName={}", fileName);
        String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Long> deleteOne(@PathVariable Long fileId) {
        log.info("delete {}", fileId);
        FileEntity file = fileService.findOne(fileId); // ! TODO :  NoFileException
        String storeFileName = file.getStoreFileName();
        String fullPath = fileService.getFullPath(storeFileName);

        File target = new File(fullPath);
        if (target.exists()) {
            if (target.delete()) {
                log.info("Success to delete {}", fullPath);
            } else {
                log.info("Fail to delete {}", fullPath);
            }
        } else {
            log.info("No such file {}", fullPath);
        }
        fileService.deleteOne(fileId);
        return ResponseEntity.ok()
                .body(fileId);
    }
}
