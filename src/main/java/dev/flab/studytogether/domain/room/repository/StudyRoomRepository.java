package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.StudyRoom;

import java.util.Optional;

public interface StudyRoomRepository {

    StudyRoom save(String roomName, int total, int memberSeqId);
    Optional<StudyRoom> findByRoomId(int roomId);
    void update(int roomId, String roomName, int maxParticipants, int currentParticipants, int managerSequenceId);
    List<StudyRoom> findByActivatedTrue();
}
