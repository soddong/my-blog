package study.wild.common.exception;

import jakarta.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    private static final String MESSAGE = "Comment not found";

    public CommentNotFoundException() {
        super(MESSAGE);
    }
}
