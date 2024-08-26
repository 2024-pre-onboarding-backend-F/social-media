package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.User;

public interface CodeRepository extends JpaRepository<Code, Long> {
    // 사용자별 인증코드 중복확인
    boolean existsByUserAndAuthCode(User user, String newAuthCode);
}
