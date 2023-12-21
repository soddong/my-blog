package study.wild.common.exception;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "Category not found";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
