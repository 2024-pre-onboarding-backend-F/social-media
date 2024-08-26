package wanted.media.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.user.dto.*;
import wanted.media.user.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Validated @RequestBody SignUpRequest request) {
        SignUpResponse response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     * 가입승인 API
     * 회원등급 (normal -> premium)
     * */
    @PostMapping("/approve")
    public ResponseEntity<VerifyResponse> approveSignUp(@RequestBody VerifyRequest request) {
        VerifyResponse response = userService.approveSignUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 인증코드 재발급 요청 API
    @PostMapping("/reissue-code")
    public ResponseEntity<ReissueCodeResponse> reissueCode(@RequestBody ReissueCodeRequest request) {
        ReissueCodeResponse response = userService.reissueCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
