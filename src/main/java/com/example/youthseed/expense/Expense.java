package com.example.youthseed.expense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expense_id; // 소비 건 당 식별

    @Column(nullable = false)
    private Long kakao_id; // 계정 식별

    @Column(nullable = false)
    private String category; // 소비 카테고리

    @Column(nullable = false)
    private int amount; // 소비 금액

    @Column(nullable = false)
    private LocalDate date; // 소비 날짜

    @Column(nullable = false)
    private String description; // 소비 설명

}
