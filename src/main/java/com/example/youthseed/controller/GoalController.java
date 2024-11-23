package com.example.youthseed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.youthseed.entity.Goal;
import com.example.youthseed.service.GoalService;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // 모든 Goal 조회
    @GetMapping
    public List<Goal> getAllGoals() {
        return goalService.getAllGoals();
    }

    // Goal ID로 Goal 조회
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable("id") Long goalId) {
        Optional<Goal> goal = goalService.getGoalById(goalId);
        return goal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Goal 생성
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal createdGoal = goalService.createGoal(goal);
        return ResponseEntity.status(201).body(createdGoal);
    }

    // Goal 수정
    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable("id") Long goalId, @RequestBody Goal updatedGoal) {
        Goal goal = goalService.updateGoal(goalId, updatedGoal);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }

    // Goal 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable("id") Long goalId) {
        return goalService.deleteGoal(goalId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
