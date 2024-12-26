package com.project.rentcar.controller;

import com.project.rentcar.domain.service.UserService;
import com.project.rentcar.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtService jwtService;

    // 로그인 폼 화면 제공 (GET 방식)
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.jsp를 반환
    }

    // 로그인 기능 (POST 방식)
    @PostMapping("/api/user/login")
    public ResponseEntity<String> login(@RequestParam String memberId, @RequestParam String memberPw) {
        // 로그인 서비스에서 memberId와 memberPw를 비교
        String result = userService.login(memberId, memberPw);

        if (result.equals("로그인 성공")) {
            // 로그인 성공 시 userIdx 조회
            Long userIdx = userService.getUserIdxByMemberId(memberId);
            String memberEmail = userService.getUserEmailByMemberId(memberId); // 이메일 조회

            // JWT 토큰 생성
            String jwtToken = jwtService.createJwt(userIdx, memberId, memberEmail);

            // JWT를 클라이언트로 전달 (헤더에 포함)
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken);  // Authorization 헤더에 JWT 추가

            // 성공 응답과 함께 JWT 토큰을 반환
            return ResponseEntity.ok()
                    .headers(headers)
                    .body("로그인 성공 및 JWT 발급");
        } else {
            // 로그인 실패 시
            return ResponseEntity.status(401).body(result); // 상태 코드 401 반환
        }
    }

    // 로그아웃 처리 메서드 (POST 요청)
    @PostMapping("/api/user/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 로그아웃 처리 (쿠키 삭제)
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);  // 쿠키를 삭제하려면 만료 시간을 0으로 설정
        cookie.setPath("/");  // 전체 경로에서 유효

        // 쿠키를 응답에 추가
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
