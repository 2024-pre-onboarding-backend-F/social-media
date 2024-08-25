package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // 사용자 계정으로 회원 조회
    Optional<User> findByAccount(String account);

    // 사용자 이메일로 회원 조회
    Optional<User> findByEmail(String email);

    // 가입인증 회원 등급 변경
    @Modifying
    @Query("UPDATE User u SET u.grade = :grade WHERE u.account = :account")
    void updateUserGrade(@Param("account") String account, @Param("grade") Grade grade);
}
