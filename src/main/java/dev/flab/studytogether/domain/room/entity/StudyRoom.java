package dev.flab.studytogether.domain.room.entity;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;

@Builder
@Getter
public class StudyRoom {
    private long roomId;
    private String roomName;
    private int maxParticipants;
    private LocalDateTime roomCreateDateTime;
    private Participants participants;
    private ActivateStatus activateStatus;

    public void enterRoom(Participant participant) {
        participants.addParticipant(participant);
    }

    public void exitRoom(Participant participant) {
        participants.removeParticipant(participant);
    }

    public boolean isRoomFull() {
        return this.maxParticipants == participants.getCurrentParticipantsCount();
    }

    public void changeRoomManager(){
        Participant currentRoomManager = participants.getRoomManger();
        Optional<Participant> nextRoomManager = findNextManager();

        if (nextRoomManager.isEmpty()) {
            throw new NoSuchElementException("방장 권한을 위임할 사용자가 존재하지 않습니다.");
        }

        changeParticipantRole(currentRoomManager, ParticipantRole.ORDINARY_PARTICIPANT);
        changeParticipantRole(nextRoomManager.get(), ParticipantRole.ROOM_MANAGER);
    }

    private Optional<Participant> findNextManager() {
        return participants.getParticipants()
                .stream()
                .filter(participant -> !participant.getParticipantRole().equals(ParticipantRole.ROOM_MANAGER))
                .min(Comparator.comparing(Participant::getRoomEntryTime));
    }

    public boolean isRoomManager(Participant participant) {
        return participants.getRoomManger().equals(participant);
    }

    public boolean isMemberExists(Participant participant) {
        return participants.hasParticipant(participant);
    }

    public int getCurrentParticipantsCount() {
        return participants.getCurrentParticipantsCount();
    }

    public Participant getRoomManager() {
        return participants.getRoomManger();
    }

    private void changeParticipantRole(Participant participant, ParticipantRole roleToChange) {
        participants.getParticipants().stream()
                .filter(p -> p.equals(participant))
                .findFirst()
                .ifPresent(p -> p.changeRole(roleToChange));
    }
}
