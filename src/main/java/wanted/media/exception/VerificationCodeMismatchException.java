package wanted.media.exception;

public class VerificationCodeMismatchException extends BaseException {
    public VerificationCodeMismatchException() {
        super(ErrorCode.VERIFICATION_CODE_MISMATCH);
    }
}
