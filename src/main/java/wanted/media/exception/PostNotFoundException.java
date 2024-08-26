package wanted.media.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

}