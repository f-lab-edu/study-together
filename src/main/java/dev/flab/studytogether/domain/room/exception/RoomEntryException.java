package dev.flab.studytogether.domain.room.exception;

import dev.flab.studytogether.exception.BadRequestException;

public class RoomEntryException extends BadRequestException {
    public RoomEntryException(String message) {
        super(message);
    }
}
