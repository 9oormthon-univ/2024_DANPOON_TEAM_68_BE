package com.example.youthseed.auth.entity;


import com.example.youthseed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email") // 외래 키 매핑
    private Member member;

    @Column(nullable = false)
    private Instant expiryDate;
}
