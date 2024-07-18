package dev.flab.studytogether.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomCreateRequest {
    private String roomName;
    private int maxParticipants;

    private RoomCreateRequest() {}
}
