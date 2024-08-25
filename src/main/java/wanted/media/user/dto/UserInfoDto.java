package wanted.media.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.media.user.domain.Grade;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoDto {
    private String account;
    private String email;
    private Grade grade; // 현재 회원등급
}
