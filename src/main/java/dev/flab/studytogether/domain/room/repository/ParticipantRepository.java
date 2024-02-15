package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.Participant;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ParticipantRepository {
    void save(int roomId, int seqId, LocalDateTime now);
    void delete(int roomId, int seqId);
    int countTotalParticipantsNum(int roomId) throws SQLException;
    boolean isMemberExists(int roomId, int memberSequenceId);
    List<Participant> findByRoomId(int roomId);
}
