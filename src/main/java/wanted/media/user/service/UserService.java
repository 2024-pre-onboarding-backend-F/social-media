package wanted.media.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.User;
import wanted.media.user.dto.*;
import wanted.media.user.repository.CodeRepository;
import wanted.media.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final GenerateCode generateCode;


    //회원가입
    public SignUpResponse signUp(SignUpRequest request) {
        // 1. 사용자 입력내용 검증
        userValidator.validateRequest(request);
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 3. 인증코드 생성
        String verificationCode = generateCode.codeGenerate();
        // 4. User 객체 생성
        User user = User.builder()
                .account(request.getAccount())
                .email(request.getEmail())
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
        // 8. UserInfoDto 생성
        UserInfoDto userInfoDto = new UserInfoDto(user.getAccount(), user.getEmail(), user.getGrade());
        // 9. SignUpResponse 생성
        SignUpResponse signUpResponse = new SignUpResponse("회원가입이 성공적으로 완료됐습니다.", userInfoDto, verificationCode);

        return signUpResponse;
    }

    //가입승인
    public VerifyResponse approveSignUp(VerifyRequest verifyRequest) {
        // 1. account로 사용자 조회
        User user = userRepository.findByAccount(verifyRequest.getAccount())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(verifyRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        // 3. 사용자 인증코드 검증
        Code code = codeRepository.findByUserAndAuthCode(user, verifyRequest.getInputCode())
                .orElseThrow(() -> new RuntimeException("인증코드가 일치하지 않습니다."));
        // 4. 인증코드 유효성 검증 (유효시간 15분)
        if (code.getCreatedTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("만료된 인증코드입니다.");
        }
        // 5. 인증 완료 -> 회원 등급 변경 (normal -> premium)
        userRepository.updateUserGrade(user.getAccount(), Grade.PREMIUM_USER);
        // 6. 인증 완료 회원 인증코드 삭제
        codeRepository.deleteByUser(user);
        // 7. 변경된 사용자정보 다시 조회
        User updateUserInfo = userRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return new VerifyResponse("인증이 성공적으로 완료되었습니다!",
                new UserInfoDto(updateUserInfo.getAccount(), updateUserInfo.getEmail(), updateUserInfo.getGrade()));
    }

}
