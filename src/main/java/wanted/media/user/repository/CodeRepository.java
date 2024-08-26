package wanted.media.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    // 특정 사용자 인증코드로 조회
    Optional<Code> findByUserAndAuthCode(User user, String authCode);

    //사용자가 발급받은 인증코드 삭제
    void deleteByUser(User user);

    // 사용자에 대해 모든 인증 코드 조회
    @Query("SELECT c FROM Code c WHERE c.user = :user ORDER BY c.createdTime DESC")
    List<Code> findAllByUserOrderByCreatedTimeDesc(@Param("user") User user);

}
