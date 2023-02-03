package com.openbox.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class MemberEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    Long idx;

    String id;

    String password;

    public MemberEntity(String id, String password){
        this.id = id;
        this.password = password;
    }
}
