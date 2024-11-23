package com.example.youthseed.auth.util;


import com.example.youthseed.auth.service.RefreshTokenService;
import com.example.youthseed.member.entity.Member;
import com.example.youthseed.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String registrationId = "kakao"; // 현재 카카오만 사용
        String email = (String) oAuth2User.getAttribute("kakao_account.email");
        String name = (String) ((Map<String, Object>) oAuth2User.getAttribute("properties")).get("nickname");

        if (email == null || email.isEmpty()) {
            throw new ServletException("Email not found from OAuth2 provider");
        }

        Member member = memberRepository.findById(email)
                .orElse(Member.builder()
                        .email(email)
                        .name(name)
                        .build());

        memberRepository.save(member);

        // JWT 토큰 생성
        String jwt = jwtUtil.generateJwtToken(member.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(member).getToken();

        // 응답 객체 생성
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("jwt", jwt);
        tokens.put("refreshToken", refreshToken);

        // 응답을 JSON 형식으로 클라이언트에 전달
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}