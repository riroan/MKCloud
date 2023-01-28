package com.openbox.backend.controller;

import com.openbox.backend.controller.dto.SaveFileRequest;
import com.openbox.backend.domain.FileEntity;
import com.openbox.backend.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/")
    public ResponseEntity<List<FileEntity>> getAll() {
        List<FileEntity> result = fileService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
