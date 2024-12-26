package com.project.rentcar.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyPageController {

    // 마이페이지 화면 제공
    @GetMapping("/mypage")
    public ModelAndView myPage(HttpSession session) {
        // 세션에 로그인된 사용자가 있는지 확인
        Object user = session.getAttribute("user");

        if (user == null) {
            // 로그인되지 않은 경우, 로그인 페이지로 리다이렉트
            return new ModelAndView("redirect:/login");
        }

        // 로그인된 사용자만 마이페이지 접근 가능
        ModelAndView modelAndView = new ModelAndView("mypage");
        // 마이페이지에 사용자 정보를 넘기고 싶다면 modelAndView.addObject()를 사용
        return modelAndView;
    }
}
