package wanted.media.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.user.dto.SignUpRequest;
import wanted.media.user.dto.SignUpResponse;
import wanted.media.user.dto.VerifyRequest;
import wanted.media.user.dto.VerifyResponse;
import wanted.media.user.dto.UserLoginRequestDto;
import wanted.media.user.dto.UserLoginResponseDto;
import wanted.media.user.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    //회원가입
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

}
