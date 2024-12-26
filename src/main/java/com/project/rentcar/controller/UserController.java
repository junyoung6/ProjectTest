package com.project.rentcar.controller;

import com.project.rentcar.domain.service.UserService;
import com.project.rentcar.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";  // signup.html이나 JSP 파일을 반환하는 방식
    }

    // 회원가입 처리 메서드 (POST 요청)
    @PostMapping("/api/users/signup")
    public ResponseEntity<String> signUp(@RequestParam String memberId,
                                         @RequestParam String memberPw,
                                         @RequestParam String memberNm,
                                         @RequestParam String memberEmail,
                                         HttpServletResponse response) {
        // 회원가입 로직 처리
        String result = userService.signUp(memberId, memberPw, memberNm, memberEmail);

        // 회원가입 성공 시 JWT 생성
        if ("회원가입이 완료되었습니다.".equals(result)) {
            // 회원가입 후 생성된 userIdx를 사용하여 JWT 토큰을 생성
            Long userIdx = userService.getUserIdxByMemberId(memberId);  // 사용자 ID로 userIdx 조회
            String jwtToken = jwtService.createJwt(userIdx, memberId,memberEmail);  // JWT 생성

            // JWT를 클라이언트로 전달 (쿠키에 저장)
            Cookie jwtCookie = new Cookie("jwtToken", jwtToken);  // JWT 토큰을 쿠키에 저장
            jwtCookie.setHttpOnly(true);  // JavaScript에서 쿠키 접근 방지
            jwtCookie.setMaxAge(60 * 60 * 24 * 365);  // 1년 동안 유효
            jwtCookie.setPath("/");  // 전체 경로에서 유효

            // 쿠키를 응답에 추가
            response.addCookie(jwtCookie);

            // 성공 응답 반환
            return ResponseEntity.ok("회원가입 성공 및 JWT 발급");
        }

        // 회원가입 실패 시
        return ResponseEntity.status(400).body(result);  // 실패한 경우 오류 메시지 반환
    }


}
