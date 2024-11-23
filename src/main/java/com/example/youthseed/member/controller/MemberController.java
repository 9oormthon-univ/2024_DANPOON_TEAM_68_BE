package com.example.youthseed.member.controller;

import com.example.youthseed.member.entity.Member;
import com.example.youthseed.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 사용자 추가 정보 입력
    @PostMapping("/additional-info")
    public ResponseEntity<String> saveAdditionalInfo(@RequestBody AdditionalInfoRequest request) {
        // 이메일로 사용자 조회
        Member member = memberRepository.findById(request.email())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 은행과 계좌번호 업데이트
        member.setBank(request.bank());
        member.setAccount(request.account());
        memberRepository.save(member);

        return ResponseEntity.ok("추가 정보가 성공적으로 저장되었습니다.");
    }
}
