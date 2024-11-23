package com.example.youthseed.auth.controller;


import com.example.youthseed.auth.entity.RefreshToken;
import com.example.youthseed.auth.service.RefreshTokenService;
import com.example.youthseed.auth.util.JwtUtil;
import com.example.youthseed.member.entity.Member;
import com.example.youthseed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Member member = memberRepository.findById(email).orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtUtil.generateJwtToken(email);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(member);

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("jwt", jwt);
        tokens.put("refreshToken", refreshToken.getToken());

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    Member member = refreshToken.getMember();
                    String newJwt = jwtUtil.generateRefreshToken(member.getEmail());
                    String newRefreshToken = refreshTokenService.createRefreshToken(member).getToken();

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("jwt", newJwt);
                    tokens.put("refreshToken", newRefreshToken);
                    return ResponseEntity.ok(tokens);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    // 로그아웃 시 리프레시 토큰 삭제
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");
        refreshTokenService.findByToken(requestRefreshToken)
                .ifPresent(refreshToken -> refreshTokenService.deleteByMember(refreshToken.getMember()));

        return ResponseEntity.ok(Map.of("message", "성공적으로 로그아웃되었습니다."));
    }


}
