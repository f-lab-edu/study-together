package dev.flab.studytogether.domain.room.repository;

import java.sql.SQLException;

public interface ParticipantRepository {
    void save(int roomId, int seqId);
    void delete(int roomId, int seqId);
    int countTotalParticipantsNum(int roomId) throws SQLException;
    boolean isMemberExists(int roomId, int memberSequenceId);
}
