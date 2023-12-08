package dev.flab.studytogether.domain.room.dto;

import lombok.Data;

@Data
public class RoomCreateRequest {

    private final String roomName;
    private final int total;
}
