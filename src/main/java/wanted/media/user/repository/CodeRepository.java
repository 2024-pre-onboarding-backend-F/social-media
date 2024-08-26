package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.User;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    //인증코드 검증
    Optional<Code> findByUserAndAuthCode(User user, String authCode);

    //사용자가 발급받은 인증코드 삭제
    void deleteByUser(User user);
}
