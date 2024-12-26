package com.project.rentcar.controller;

import com.project.rentcar.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RestrictedPageController {

    private final JwtService jwtService;

    public RestrictedPageController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // 인증이 필요한 페이지 (예: 회원 전용 페이지)
    @GetMapping("/restricted")
    public String restrictedPage(HttpServletRequest request) {
        // 헤더에서 Authorization 토큰을 가져옵니다.
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Authorization 헤더에 토큰이 없다면 로그인 페이지로 리다이렉트
        if (token == null || !token.startsWith("Bearer ")) {
            return "restricted_No";  // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        // "Bearer "를 제외한 JWT 토큰 부분 추출
        String jwtToken = token.substring(7);

        try {
            // JWT 토큰 검증
            Long userIdx = jwtService.getUserIdxFromJwt(jwtToken);
            if (userIdx != null) {
                return "restricted";  // 인증된 사용자는 제한된 페이지로 이동
            }
        } catch (Exception e) {
            // 토큰 검증 실패 시 로그인 페이지로 리다이렉트
            return "restricted_No";
        }

        return "restricted_No";  // 인증되지 않은 경우 로그인 페이지로 리다이렉트
    }
}
