package com.example.youthseed.member.controller;

public record AdditionalInfoRequest(
        String email,
        String bank,
        String account
) {
}
