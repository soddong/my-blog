package study.wild.common.validator;

import study.wild.common.exception.EmptyInputException;

public class ContentValidator {

    private ContentValidator() {
    }

    public static void validateContent(final String title) {
        validateEmpty(title);
    }

    private static void validateEmpty(String title) {
        if (title.isEmpty()) {
            throw new EmptyInputException();
        }
    }
}
