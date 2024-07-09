package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.StudyRoom;

import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository {
    StudyRoom save(StudyRoom studyRoom);
    Optional<StudyRoom> findByRoomId(long roomId);
    void update(StudyRoom studyRoom);
    List<StudyRoom> findByActivatedTrue();
}
