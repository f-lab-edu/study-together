package dev.flab.studytogether.domain.room.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Participants {
    private final List<Participant> participants;

    public Participants(List<Participant> participants) {
        this.participants = new ArrayList<>(participants);
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public int getCurrentParticipantsCount() {
        return participants.size();
    }

    public Participant getRoomManger() {
        return participants.stream()
                .filter(participant ->
                        participant.getParticipantRole().equals(ParticipantRole.ROOM_MANAGER))
                .findFirst()
                .get();
    }

    public boolean hasParticipant(Participant participant) {
        return participants.contains(participant);
    }
}
