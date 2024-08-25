package wanted.media.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VerifyResponse {
    private String message;
    private UserInfoDto userInfo; //사용자 정보
}
