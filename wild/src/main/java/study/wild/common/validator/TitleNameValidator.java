package study.wild.common.validator;

import study.wild.common.exception.EmptyInputException;
import study.wild.common.exception.LengthLimitExceededException;

public class TitleNameValidator {
    private TitleNameValidator() {
    }

    public static void validateTitleName(final String title) {
        validateEmpty(title);
        validateLength(title);
    }

    private static void validateLength(String title) {
        if (title.length() > 50) {
            throw new LengthLimitExceededException();
        }
    }

    private static void validateEmpty(String title) {
        if (title.isEmpty()) {
            throw new EmptyInputException();
        }
    }
}
