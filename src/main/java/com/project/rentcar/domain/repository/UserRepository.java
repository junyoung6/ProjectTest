package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 'memberId'는 엔티티의 Java 필드명입니다.
    Optional<User> findByMemberId(String memberId);  // 필드명 'memberId'로 작성

    // 'memberEmail'은 엔티티의 Java 필드명입니다.
    Optional<User> findByMemberEmail(String memberEmail);  // 필드명 'memberEmail'로 작성

}
