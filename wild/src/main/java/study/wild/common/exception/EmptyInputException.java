package study.wild.common.exception;

public class EmptyInputException extends RuntimeException {
    private static String MESSAGE = "Value is empty";

    public EmptyInputException() {
        super(MESSAGE);
    }
}
