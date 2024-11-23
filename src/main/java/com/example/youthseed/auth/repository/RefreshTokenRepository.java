package com.example.youthseed.auth.repository;

import com.example.youthseed.auth.entity.RefreshToken;
import com.example.youthseed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByMember(Member member);
}
