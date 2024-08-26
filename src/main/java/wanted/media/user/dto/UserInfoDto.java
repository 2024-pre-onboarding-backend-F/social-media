package wanted.media.user.dto;

import wanted.media.user.domain.Grade;

public record UserInfoDto(String account, String email, Grade grade) {
}