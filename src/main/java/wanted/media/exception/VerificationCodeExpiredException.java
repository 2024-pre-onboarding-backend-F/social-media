package wanted.media.exception;

public class VerificationCodeExpiredException extends BaseException {
    public VerificationCodeExpiredException() {
        super(ErrorCode.VERIFICATION_CODE_EXPIRED);
    }
}
