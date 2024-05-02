package dev.flab.studytogether.domain.room.entity;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Arrays;

@Builder
public class StudyRoom {
    @Getter
    private int roomId;
    @Getter
    private String roomName;
    @Getter
    private int maxParticipants;
    @Getter
    private int currentParticipants;
    private LocalDate createDate;
    private ActivateStatus activateStatus;
    @Getter
    private int managerSequenceId;

    public void enterRoom() {
        this.currentParticipants++;
    }

    public void exitRoom() {
        this.currentParticipants--;
    }

    public boolean isRoomFull() {
        return this.maxParticipants == this.currentParticipants;
    }

    public void changeRoomManager(int newManagerSequenceID){
        this.managerSequenceId = newManagerSequenceID;
    }

    public boolean isRoomManager(int memberSequenceID) {
        return this.managerSequenceId == memberSequenceID;
    }


}
