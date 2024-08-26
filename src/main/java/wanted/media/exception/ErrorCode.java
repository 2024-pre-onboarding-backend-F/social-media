package wanted.media.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 엔티티입니다."),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");

	private final HttpStatus status;
	private final String message;
}
