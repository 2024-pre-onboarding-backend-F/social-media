package wanted.media.user.dto;

public record SignUpResponse(String message, UserCreateDto dto, String authCode) {
}
