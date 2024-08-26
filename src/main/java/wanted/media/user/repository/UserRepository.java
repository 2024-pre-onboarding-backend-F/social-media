package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // 사용자 계정으로 회원 조회
    Optional<User> findByAccount(String account);

    // 사용자 이메일로 회원 조회
    Optional<User> findByEmail(String email);
}
