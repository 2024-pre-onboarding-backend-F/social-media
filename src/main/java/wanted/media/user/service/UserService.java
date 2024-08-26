package wanted.media.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.media.user.config.TokenProvider;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.Token;
import wanted.media.user.domain.User;
import wanted.media.user.dto.*;
import wanted.media.user.repository.CodeRepository;
import wanted.media.user.repository.TokenRepository;
import wanted.media.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    private final CodeRepository codeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final GenerateCode generateCode;

    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) {
        User user = userRepository.findByAccount(requestDto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("account나 password를 다시 확인해주세요."));
        if (!requestDto.getPassword().equals(user.getPassword())) // password 암호화 저장시 변경하기
            throw new IllegalArgumentException("account나 password를 다시 확인해주세요.");

        Optional<Token> refreshToken = tokenRepository.findByUserId(user.getUserId()); // 리프레시 토큰 있는지 확인
        String newRefreshToken = tokenProvider.makeToken(requestDto.getAccount(), "refresh"); // 새 리프레시 토큰
        if (refreshToken.isPresent()) { // 리프레시 토큰 있을 경우
            tokenRepository.save(refreshToken.get().updateToken(newRefreshToken)); // 새 토큰으로 업데이트
        } else { // 리프레시 토큰 없을 경우
            tokenRepository.save(new Token(newRefreshToken, user)); // 새 토큰 저장
        }

        return new UserLoginResponseDto(user.getUserId(), tokenProvider.makeToken(requestDto.getAccount(), "access"));
    }

    //회원가입
    public SignUpResponse signUp(SignUpRequest request) {
        // 1. 사용자 입력내용 검증
        userValidator.validateRequest(request);
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());
        // 3. 인증코드 생성
        String verificationCode = generateCode.codeGenerate();
        // 4. User 객체 생성
        User user = User.builder()
                .account(request.account())
                .email(request.email())
                .password(encodedPassword)
                .grade(Grade.NORMAL_USER)
                .build();
        // 5. 사용자 db 저장
        userRepository.save(user);
        // 6. Code 객체 생성
        Code code = Code.builder()
                .user(user)
                .authCode(verificationCode)
                .createdTime(LocalDateTime.now())
                .build();
        // 7. 인증코드 db 저장
        codeRepository.save(code);
        // 8. UserCreateDto 생성
        UserCreateDto userCreateDto = new UserCreateDto(user.getAccount(), user.getEmail());
        // 9. SignUpResponse 생성 및 반환
        return new SignUpResponse("회원가입이 완료되었습니다.", userCreateDto, verificationCode);
    }
}
