package study.wild.exception;

import jakarta.persistence.EntityExistsException;

public class NonEmptyCategoryException extends EntityExistsException {
    private static final String MESSAGE = "Posts exist in the category";

    public NonEmptyCategoryException() {
        super(MESSAGE);
    }
}
