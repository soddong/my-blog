package study.wild.common.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
