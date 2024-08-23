package wanted.media.user.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import wanted.media.user.domain.UserDetail;
import wanted.media.user.service.UserDetailService;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("&{jwt.secret_key}")
    private String key;

    private long tokenValidTime = 1000L * 60 * 60; // 1시간

    private final UserDetailService userDetailService;

    public String makeToken(String account) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입
                .setIssuedAt(now) // 발급시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료시간
                .setClaims(Jwts.claims().setSubject(account)) // 회원 계정 (사용자 식별값)
                .signWith(SignatureAlgorithm.HS256, key) // HS256 방식으로 key와 함께 암호화
                .compact();
    }

    // 토큰 유효성 검증
    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰으로 인증 정보 담은 Authentication 반환
    public Authentication getAuthentication(String token) {
        UserDetail userDetail = (UserDetail) userDetailService.loadUserByUsername(getUserAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
        /* principal : 인증된 사용자 정보
            credentials : 사용자의 인증 자격 증명 (인증 완료된 상태이므로 빈 문자열 사용)
            authorities : 사용자의 권한목록*/
    }

    public String getUserAccount(String token) {
        try { // JWT를 파싱해서 JWT 서명 검증 후 클레임을 반환하여 payload에서 subject 클레임 추출
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    // 토큰 Header에서 꺼내오기
    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            return header.substring(7);
        return null;
    }
}
