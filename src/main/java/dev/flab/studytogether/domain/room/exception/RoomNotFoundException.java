package dev.flab.studytogether.domain.room.exception;

import dev.flab.studytogether.exception.NotFoundException;

public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
