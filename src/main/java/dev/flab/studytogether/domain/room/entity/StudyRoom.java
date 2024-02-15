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

    public enum ActivateStatus {
        ACTIVATED(true),
        TERMINATED(false);

        private final boolean statusValue;

        ActivateStatus(boolean statusValue) {
            this.statusValue = statusValue;
        }

        public boolean getStatusValue() {
            return statusValue;
        }


        public static ActivateStatus findByStatus(final boolean status) {
            return Arrays.stream(ActivateStatus.values())
                    .filter(e -> e.statusValue == status)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
        }
    }


}
