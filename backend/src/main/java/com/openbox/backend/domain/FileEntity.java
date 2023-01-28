package com.openbox.backend.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    @Positive
    private Long fileSize;

    @NotBlank
    private String owner;

    @NotNull
    private LocalDateTime uploadTime;
}
