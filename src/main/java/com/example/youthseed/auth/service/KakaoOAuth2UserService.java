package com.example.youthseed.auth.service;

import com.example.youthseed.auth.util.JwtUtil;
import com.example.youthseed.member.entity.Member;
import com.example.youthseed.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KakaoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 제공자 정보
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao"
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // "id"

        // 사용자 속성
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = getEmail(attributes, registrationId);
        String name = getName(attributes, registrationId);

        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_email"), "Email not found from OAuth2 provider");
        }

        // 사용자 저장 또는 업데이트 (bank와 account는 추가 정보 입력 시 업데이트)
        Member member = memberRepository.findById(email)
                .orElse(Member.builder()
                        .email(email)
                        .name(name)
                        .build());

        memberRepository.save(member);

        // JWT 토큰 생성 (토큰은 이후 SuccessHandler에서 클라이언트로 전달)
        String token = jwtUtil.generateJwtToken(member.getEmail());

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                userNameAttributeName);
    }

    private String getEmail(Map<String, Object> attributes, String registrationId) {
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        }

        return null;
    }

    private String getName(Map<String, Object> attributes, String registrationId) {
        if ("kakao".equals(registrationId)) {
            return (String) ((Map<String, Object>) attributes.get("properties")).get("nickname");
        }

        return null;
    }
}
