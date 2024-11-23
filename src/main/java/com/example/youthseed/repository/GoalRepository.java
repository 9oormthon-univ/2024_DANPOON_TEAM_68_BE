package com.example.youthseed.repository;

import com.example.youthseed.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    // 커스텀 쿼리가 필요하다면 여기에 추가
}
