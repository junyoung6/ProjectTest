package com.project.rentcar.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // JWT 서명에 사용할 비밀키

    // JWT 생성 메서드
    public String createJwt(Long userIdx, String memberId, String memberEmail) {
        Date now = new Date();
        // JWT 생성 (유효기간 1년 설정)
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")  // 타입을 JWT로 설정
                .claim("userIdx", userIdx)  // userIdx를 클레임으로 추가
                .claim("memberId", memberId)  // memberId를 클레임으로 추가
                .claim("memberEmail", memberEmail)  // memberEmail을 클레임으로 추가
                .setIssuedAt(now)  // 토큰 발급 시간
                .setExpiration(new Date(now.getTime() + 1000L * 60 * 60 * 24 * 365)) // 1년 후 만료
                .signWith(key)  // 비밀키로 서명
                .compact();
    }

    // JWT 토큰에서 userIdx 추출하는 메서드
    public Long getUserIdxFromJwt(String jwtToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)  // 비밀키로 서명 검증
                    .build()
                    .parseClaimsJws(jwtToken)  // JWT 파싱
                    .getBody();
            return claims.get("userIdx", Long.class);  // userIdx 클레임 추출
        } catch (Exception e) {
            return null;  // 예외 발생 시 null 반환
        }
    }

}
