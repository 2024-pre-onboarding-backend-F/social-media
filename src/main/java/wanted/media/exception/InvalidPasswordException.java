package wanted.media.exception;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException() {

        super(ErrorCode.INVALID_PASSWORD);
    }
}
