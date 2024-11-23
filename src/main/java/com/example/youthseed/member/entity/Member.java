package com.example.youthseed.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {

    @Id
    @Column(nullable = false, unique = true)
    private String email; // 카카오 이메일을 ID로 사용

    private String name;

    private String bank;

    private String account;
}
