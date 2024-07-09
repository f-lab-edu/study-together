package dev.flab.studytogether.domain.room.dto;


import dev.flab.studytogether.domain.room.entity.StudyRoom;
import lombok.Getter;

@Getter
public class StudyRoomResponse {
    private final long roomId;
    private final String roomName;
    private final int maxParticipants;
    private final int currentParticipantsCount;
    private final int roomManagerSequenceId;


    private StudyRoomResponse(long roomId, String roomName, int maxParticipants, int currentParticipants, int roomManagerSequenceId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxParticipants = maxParticipants;
        this.currentParticipantsCount = currentParticipants;
        this.roomManagerSequenceId = roomManagerSequenceId;
    }

    public static StudyRoomResponse from(StudyRoom studyRoom) {
        return new StudyRoomResponse(studyRoom.getRoomId(),
                studyRoom.getRoomName(),
                studyRoom.getMaxParticipants(),
                studyRoom.getCurrentParticipantsCount(),
                studyRoom.getRoomManager().getMemberSequenceId());
    }
}
