package com.project.rentcar.domain.service;

import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    public String signUp(String memberId, String memberPw, String memberNm, String memberEmail) {
        // 중복 ID 검사
        if (userRepository.findByMemberId(memberId).isPresent()) {
            return "이미 존재하는 사용자 ID입니다.";
        }

        // 중복 이메일 검사
        if (userRepository.findByMemberEmail(memberEmail).isPresent()) {
            return "이미 등록된 이메일입니다.";
        }

        // 비밀번호 암호화 처리
        String encryptedPw = passwordEncoder.encode(memberPw);

        // 사용자 저장
        User user = User.builder()
                .memberId(memberId)
                .memberPw(encryptedPw)  // 비밀번호 암호화
                .memberNm(memberNm)
                .memberEmail(memberEmail)
                .build();

        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    // 로그인
    public String login(String memberId, String memberPw) {
        Optional<User> userOptional = userRepository.findByMemberId(memberId);

        // 사용자 확인
        if (userOptional.isEmpty()) {
            return "존재하지 않는 사용자입니다.";
        }

        User user = userOptional.get();

        // 비밀번호 확인
        if (!passwordEncoder.matches(memberPw, user.getMemberPw())) {
            return "잘못된 비밀번호입니다.";
        }

        return "로그인 성공";
    }

    // 사용자 ID로 userIdx 조회
    public Long getUserIdxByMemberId(String memberId) {
        Optional<User> userOptional = userRepository.findByMemberId(memberId);
        return userOptional.map(User::getUserIdx).orElse(null);
    }
    // 사용자 ID로 이메일 조회
    public String getUserEmailByMemberId(String memberId) {
        Optional<User> userOptional = userRepository.findByMemberId(memberId);
        return userOptional.map(User::getMemberEmail).orElse(null);
    }
}
