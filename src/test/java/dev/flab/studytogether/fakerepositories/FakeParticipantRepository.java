package dev.flab.studytogether.fakerepositories;

import dev.flab.studytogether.domain.room.entity.Participant;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeParticipantRepository implements ParticipantRepository {
    private final Collection<Participant> fakeParticipants = new ArrayList<>();
    @Override
    public void save(Participant participant) {
        fakeParticipants.add(participant);
    }

    @Override
    public void delete(Participant participant) {
        fakeParticipants.remove(participant);
    }

    @Override
    public List<Participant> findByRoomId(long roomId) {
        return fakeParticipants.stream()
                .filter(x -> x.getRoomId() == roomId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Participant> findByMemberId(int memberSequenceId) {
        return fakeParticipants.stream()
                .filter(x-> x.getMemberSequenceId() == memberSequenceId)
                .findFirst();
    }
}
