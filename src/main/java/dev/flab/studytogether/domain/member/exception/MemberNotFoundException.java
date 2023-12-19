package dev.flab.studytogether.domain.member.exception;


import dev.flab.studytogether.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
