package study.wild.common.exception;

public class LengthLimitExceededException extends RuntimeException {
    private static final String MESSAGE = "Length Limit exceeded";

    public LengthLimitExceededException() {
        super(MESSAGE);
    }
}
