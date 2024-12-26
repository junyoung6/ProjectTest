package com.project.rentcar.domain.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String memberId;  // 아이디
    private String memberPw;  // 비밀번호
}
