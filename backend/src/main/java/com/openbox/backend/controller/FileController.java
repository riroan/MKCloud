package com.openbox.backend.controller;

import com.openbox.backend.domain.FileEntity;
import com.openbox.backend.service.FileService;
import com.openbox.backend.support.Login;
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
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@Login String user, List<MultipartFile> multipartFiles) {
        log.info("user : {}", user);
        if (multipartFiles == null) {
            log.info("no files");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (MultipartFile multipartFile : multipartFiles) {
            log.info("file = {}", multipartFile);
        }
        fileService.saveAll(multipartFiles, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileEntity>> getAll(@Login String user) {
        log.info("user : {}", user);
        List<FileEntity> result = fileService.findByOwner(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadOne(@Login String user, @PathVariable Long fileId) throws MalformedURLException {
        FileEntity file = fileService.findOne(fileId); // ! TODO :  NoFileException

        if(!file.getOwner().equals(user)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        String storeFileName = file.getStoreFileName();
        String fileName = file.getFileName();

        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(storeFileName, user));
        log.info("uploadFileName={}", fileName);
        String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteOne(@Login String user, @PathVariable Long fileId) {
        log.info("delete {}", fileId);
        FileEntity file = fileService.findOne(fileId); // ! TODO :  NoFileException
        String storeFileName = file.getStoreFileName();
        String fullPath = fileService.getFullPath(storeFileName, user);

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
        fileService.deleteOne(fileId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
