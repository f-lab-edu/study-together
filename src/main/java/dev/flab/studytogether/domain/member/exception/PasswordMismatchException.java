package dev.flab.studytogether.domain.member.exception;

import dev.flab.studytogether.exception.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
