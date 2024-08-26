package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.media.user.dto.SignUpRequest;
import wanted.media.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    /*
     * 회원가입 시 사용자 정보 중복 확인
     * */
    public void validateRequest(SignUpRequest signUpRequest) {
        //account 중복 확인
        if (userRepository.findByAccount(signUpRequest.account()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 계정입니다.");
        }
        //email 중복 확인
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        //비밀번호 유효성 검사
        validatePassword(signUpRequest.password(), signUpRequest.account(), signUpRequest.email());
    }

    //비밀번호 유효성 검사
    private void validatePassword(String password, String account, String email) {
        // 1. 10자리 미만일 경우
        if (password.length() < 10) {
            throw new IllegalArgumentException("비밀번호는 최소 10자리 이상으로 설정해주세요.");
        }
        // 2. 숫자로만 설정된 경우
        if (password.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("비밀번호가 숫자로만 이루어졌습니다.");
        }
        // 3. 숫자, 문자, 특수문자 중 최소 2가지 이상 포함하지 않은 경우
        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*[0-9!@#$%^&*].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 숫자, 문자, 특수문자 중 최소 2가지를 포함해야 합니다.");
        }
        // 4. 연속된 문자열을 사용한 경우
        if (hasSequentialCharacters(password)) {
            throw new IllegalArgumentException("3회 이상 연속되는 문자 사용은 불가능합니다.");
        }
        // 5. 사용자 개인정보를 포함한 경우
        if (isSimilarToPersonalInfo(password, account, email)) {
            throw new IllegalArgumentException("사용자 개인정보가 포함된 비밀번호입니다.");
        }
    }

    // 3회 이상 연속된 문자열을 사용한 비밀번호 검사
    private boolean hasSequentialCharacters(String password) {
        if (password == null || password.length() < 3) {
            return false;
        }
        int seqCount = 1;
        for (int i = 1; i < password.length(); i++) {
            // 현재 문자와 이전 문자의 차이를 비교
            if (password.charAt(i) == password.charAt(i - 1) + 1) {
                seqCount++;
                if (seqCount >= 3) {
                    return true;
                }
            } else {
                seqCount = 1;
            }
        }
        return false;
    }

    // 사용자 개인정보를 포함한 경우 비밀번호 검사
    private boolean isSimilarToPersonalInfo(String password, String account, String email) {
        String emailLocalPart = email.split("@")[0]; // email 사용자명 부분
        return password.toLowerCase().contains(emailLocalPart.toLowerCase()) ||
                password.toLowerCase().contains(account.toLowerCase());
    }
}
