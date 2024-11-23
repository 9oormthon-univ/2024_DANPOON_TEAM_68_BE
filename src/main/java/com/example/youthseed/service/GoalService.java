package com.example.youthseed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.youthseed.entity.Goal;
import com.example.youthseed.repository.GoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    // 모든 Goal 조회
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    // Goal ID로 Goal 조회
    public Optional<Goal> getGoalById(Long goalId) {
        return goalRepository.findById(goalId);
    }

    // Goal 저장
    @Transactional
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    // Goal 업데이트
    @Transactional
    public Goal updateGoal(Long goalId, Goal updatedGoal) {
        if (goalRepository.existsById(goalId)) {
            updatedGoal.setGoalId(goalId);
            return goalRepository.save(updatedGoal);
        }
        return null;
    }

    // Goal 삭제
    @Transactional
    public boolean deleteGoal(Long goalId) {
        if (goalRepository.existsById(goalId)) {
            goalRepository.deleteById(goalId);
            return true;
        }
        return false;
    }
}
