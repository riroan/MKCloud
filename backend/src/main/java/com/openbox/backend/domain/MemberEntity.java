package com.openbox.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    Long idx;

    String id;

    String password;

    Long capacity;

    public MemberEntity(String id, String password) {
        this.id = id;
        this.password = password;
        this.capacity = 1024 * 1024 * 1024 * 10L;
    }
}
