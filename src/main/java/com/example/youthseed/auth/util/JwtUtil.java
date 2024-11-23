package com.example.youthseed.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    @Value("${jwt.refreshSecret}")
    private String refreshSecret;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshExpirationMs;

    // JWT 토큰 생성
    public String generateJwtToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // JWT 토큰에서 이메일 추출
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // JWT 토큰 유효성 검사
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            // 로그 처리
        }
        return false;
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(SignatureAlgorithm.HS512, refreshSecret)
                .compact();
    }

    // 리프레시 토큰에서 이메일 추출
    public String getEmailFromRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(refreshSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 리프레시 토큰 유효성 검사
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 로그 처리
        }
        return false;
    }

}
