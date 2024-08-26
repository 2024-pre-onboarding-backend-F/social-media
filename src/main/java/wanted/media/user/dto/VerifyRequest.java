package wanted.media.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyRequest(
        @NotBlank @Size(max = 50) String account,
        @NotBlank @Size(min = 10, max = 200, message = "비밀번호는 최소 10자리 이상으로 설정해주세요.") String password,
        @NotBlank @Size(max = 10) String inputCode
) {
}