package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository {
    void save(Participant participant);
    void delete(Participant participant);
    List<Participant> findByRoomId(long roomId);
    Optional<Participant> findByMemberId(int memberSequenceId);
}
