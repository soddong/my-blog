package study.wild.common.exception;

import com.sun.jdi.request.DuplicateRequestException;

public class DuplicatedUserException extends DuplicateRequestException {
    public DuplicatedUserException(long id) {
        super("ID " + id + "가 이미 존재합니다.");
    }
}
