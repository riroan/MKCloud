package com.openbox.backend.repository;

import com.openbox.backend.domain.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class FileRepositoryTest {
    @Autowired
    FileRepository fileRepository;

    @AfterEach
    void afterEach() {
        if (fileRepository instanceof MemoryFileRepository) {
            ((MemoryFileRepository) fileRepository).clearStore();
        }
    }

    @Test
    @DisplayName(value = "파일 하나 저장")
    void save() {
        // given
        FileEntity file = new FileEntity("file1.jpg", "store_file1.jpg", 10L, "user1");

        // when
        FileEntity savedFile = fileRepository.save(file);

        //then
        FileEntity findFile = fileRepository.findById(savedFile.getId());
        assertThat(findFile).isEqualTo(file);
    }

    @Test
    @DisplayName(value = "파일 전부 찾기")
    void findAll() {
        FileEntity file1 = new FileEntity("file1.jpg", "store_file1.jpg", 10L, "user1");
        FileEntity file2 = new FileEntity("file2.jpg", "store_file2.jpg", 10L, "user1");
        FileEntity file3 = new FileEntity("file3.jpg", "store_file3.jpg", 10L, "user1");

        FileEntity savedFile1 = fileRepository.save(file1);
        FileEntity savedFile2 = fileRepository.save(file2);
        FileEntity savedFile3 = fileRepository.save(file3);

        List<FileEntity> result = fileRepository.findAll();
        assertThat(result).containsExactly(savedFile1, savedFile2, savedFile3);
    }

    @Test
    @DisplayName(value = "작성자로 찾기")
    void findByOwner() {
        FileEntity file1 = new FileEntity("file1.jpg", "store_file1.jpg", 10L, "user1");
        FileEntity file2 = new FileEntity("file2.jpg", "store_file2.jpg", 10L, "user2");
        FileEntity file3 = new FileEntity("file3.jpg", "store_file3.jpg", 10L, "user3");
        FileEntity file4 = new FileEntity("file4.jpg", "store_file4.jpg", 10L, "user1");
        FileEntity file5 = new FileEntity("file5.jpg", "store_file5.jpg", 10L, "user2");

        FileEntity savedFile1 = fileRepository.save(file1);
        FileEntity savedFile2 = fileRepository.save(file2);
        FileEntity savedFile3 = fileRepository.save(file3);
        FileEntity savedFile4 = fileRepository.save(file4);
        FileEntity savedFile5 = fileRepository.save(file5);

        List<FileEntity> result1 = fileRepository.findByOwner("user1");
        assertThat(result1).containsExactly(savedFile1, savedFile4);

        List<FileEntity> result2 = fileRepository.findByOwner("user2");
        assertThat(result2).containsExactly(savedFile2, savedFile5);

        List<FileEntity> result3 = fileRepository.findByOwner("user3");
        assertThat(result3).containsExactly(savedFile3);
    }

    @Test
    @DisplayName(value = "파일 하나 삭제")
    void deleteOne() {
        FileEntity file1 = new FileEntity("file1.jpg", "store_file1.jpg", 10L, "user1");
        FileEntity savedFile = fileRepository.save(file1);

        fileRepository.deleteById(savedFile.getId());

        assertThatThrownBy(() -> fileRepository.findById(savedFile.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName(value = "파일 사용량 확인")
    void getFileSizeSum() {
        FileEntity file1 = new FileEntity("file1.jpg", "store_file1.jpg", 10L, "user1");
        FileEntity file2 = new FileEntity("file2.jpg", "store_file2.jpg", 20L, "user2");
        FileEntity file3 = new FileEntity("file3.jpg", "store_file3.jpg", 30L, "user1");
        FileEntity file4 = new FileEntity("file4.jpg", "store_file4.jpg", 40L, "user2");
        FileEntity file5 = new FileEntity("file5.jpg", "store_file5.jpg", 50L, "user2");
        FileEntity file6 = new FileEntity("file6.jpg", "store_file6.jpg", 60L, "user1");

        fileRepository.save(file1);
        fileRepository.save(file2);
        fileRepository.save(file3);
        fileRepository.save(file4);
        fileRepository.save(file5);
        fileRepository.save(file6);

        assertThat(fileRepository.getFileSizeSum("user1")).isEqualTo(100L);
        assertThat(fileRepository.getFileSizeSum("user2")).isEqualTo(110L);
    }
}