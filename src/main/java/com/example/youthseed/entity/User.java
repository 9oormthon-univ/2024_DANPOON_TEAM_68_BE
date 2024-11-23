package com.example.youthseed.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "kakao_id")
    private String kakaoId;  // Email (유저 ID)

    @Column(name = "password")
    private String password;

    @Column(name = "account")
    private String account;

    @Column(name = "age")
    private Integer age;

    @Column(name = "bank")
    private String bank;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String kakaoId, String password, String account, Integer age, String bank) {
        this.kakaoId = kakaoId;
        this.password = password;
        this.account = account;
        this.age = age;
        this.bank = bank;
    }

    // Getter, Setter
    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String  getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
