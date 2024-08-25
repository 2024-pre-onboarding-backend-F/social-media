package wanted.media.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyRequest {
    @NotBlank
    @Size(max = 50)
    private String account;

    @NotBlank
    @Size(min = 10, max = 200)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String inputCode; //사용자 입력 인증코드
}
