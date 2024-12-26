package com.project.rentcar.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "User2")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generated for userIdx
    @Column(name = "user_idx")  // DB 컬럼명 'user_idx'에 매핑
    private Long userIdx;        // Java 필드명 'userIdx'

    @Column(name = "member_id")  // DB 컬럼명 'member_id'에 매핑
    private String memberId;      // Java 필드명은 'memberId'

    @Column(name = "member_pw")   // DB 컬럼명 'member_pw'에 매핑
    private String memberPw;      // Java 필드명은 'memberPw'

    @Column(name = "member_nm")   // DB 컬럼명 'member_nm'에 매핑
    private String memberNm;      // Java 필드명은 'memberNm'

    @Column(name = "member_email")// DB 컬럼명 'member_email'에 매핑
    private String memberEmail;   // Java 필드명은 'memberEmail'



    // 기본 생성자
    public User() {}

    @Builder
    public User(String memberId, String memberPw, String memberNm, String memberEmail) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberNm = memberNm;
        this.memberEmail = memberEmail;
    }
}
