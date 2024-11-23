package com.example.youthseed.repository;

import com.example.youthseed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}