package dev.flab.studytogether.domain.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class StudyRoomMessage {
    private final String studyRoomID;
    private final String memberID;
    private final int memberSequenceID;
    @Setter
    private String message;

    public StudyRoomMessage(String studyRoomID, String memberID, int memberSequenceID) {
        this.studyRoomID = studyRoomID;
        this.memberID = memberID;
        this.memberSequenceID = memberSequenceID;
    }
}