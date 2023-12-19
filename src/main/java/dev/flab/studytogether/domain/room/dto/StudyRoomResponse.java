package dev.flab.studytogether.domain.room.dto;

import lombok.Data;

@Data
public class StudyRoomResponse {
    private final int roomId;
    private final String roomName;
    private final int maxParticipants;
    private final int currentParticipants;
    private final int roomManagerSequenceId;
}
