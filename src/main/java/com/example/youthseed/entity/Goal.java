package com.example.youthseed.entity;

import jakarta.persistence.*;
import java.time.LocalDate;  // LocalDate로 변경
import com.example.youthseed.entity.User;

@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @ManyToOne
    @JoinColumn(name = "kakao_id")  // 'referencedColumnName' 생략, 기본적으로 kakao_id와 매핑
    private User user;  // User 엔티티에 kakao_id가 @Id로 정의되어 있다고 가정

    private String name;
    private String type;
    private Integer targetAmount;
    private Integer currentAmount;
    private LocalDate deadline;  // Date 대신 LocalDate로 변경
    private String status;

    // Getters and Setters

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
