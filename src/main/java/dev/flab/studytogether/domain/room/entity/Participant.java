package dev.flab.studytogether.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Participant {
    private final long roomId;
    private final int memberSequenceId;
    private ParticipantRole participantRole;
    private final LocalDateTime roomEntryTime;

    public void changeRole(ParticipantRole participantRole) {
        this.participantRole = participantRole;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Participant participant)) return false;
        return roomId == participant.roomId &&
                memberSequenceId == participant.memberSequenceId &&
                participantRole == participant.getParticipantRole() &&
                Objects.equals(roomEntryTime, participant.roomEntryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, memberSequenceId, participantRole, roomEntryTime);
    }
}
