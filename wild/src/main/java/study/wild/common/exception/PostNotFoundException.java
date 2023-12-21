package study.wild.common.exception;

import jakarta.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "Post not found";

    public PostNotFoundException() {
        super(MESSAGE);
    }
}
