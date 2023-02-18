package com.openbox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @NotBlank
    private String fileName;

    @NotBlank
    private String storeFileName;

    @NotNull
    @Positive
    private Long fileSize;

    @NotBlank
    private String owner;

    @NotNull
    private LocalDateTime uploadTime;

    @NotNull
    private Boolean isDeleted;

    public FileEntity() {
        this.uploadTime = LocalDateTime.now();
    }

    public FileEntity(final String fileName, final String storeFileName, final Long fileSize, final String owner) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
        this.fileSize = fileSize;
        this.owner = owner;
        this.uploadTime = LocalDateTime.now();
        this.isDeleted = false;
    }
}
